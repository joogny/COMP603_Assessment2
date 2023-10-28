/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import questions.Exam;
import questions.Question;
import records.Records;

/**
 *
 * @author Julien
 */
public class Model extends Observable implements Records {

    private static final String FILEPATH = "./resources/questions.txt";

    private ArrayList<Question> questionRecords;
    private Exam exam;
    private int currentQuestionIndex;
    private int score;
    private boolean canQuitSafely = false;

    public Model() {
        this.questionRecords = new ArrayList<>();
    }

    public void nextQuestion() {
        Question q = this.exam.getQuestion(currentQuestionIndex);
        this.currentQuestionIndex++;
        this.setChanged();
        if (q == null) {
            this.canQuitSafely = true;
            notifyObservers(this.score);
        } else {
            notifyObservers(q);
        }
    }

    public void addQuestion(Question question) {
        this.questionRecords.add(question);
    }

    @Override
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(FILEPATH));
            for (Iterator<Question> iterator = questionRecords.iterator(); iterator.hasNext();) {
                Question question = iterator.next();
                pw.println(question.formatForFile());
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            System.err.println("The questions file is not here, questions were not saved");
        }
    }

    @Override
    public void loadQuestions() {
        //init Model
        score = 0;
        currentQuestionIndex = 0;
        questionRecords = new ArrayList<>();

        try {
            FileReader fr = new FileReader(FILEPATH);
            BufferedReader inputStream = new BufferedReader(fr);
            String line = null;
            while ((line = inputStream.readLine()) != null) {
                String[] splitString = line.split("/");

                String courseCode = splitString[0];
                String examCode = splitString[1];
                String questionString = splitString[2];
                String[] questionStringSplit = questionString.split(":");
                String question = questionStringSplit[0];
                String answer = questionStringSplit[1];

                questionRecords.add(new Question(question, answer, examCode, courseCode));

                //temp exam
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found, couldn't get the questions");
        } catch (IOException ex) {
            System.err.println("Error reading from file " + FILEPATH + ", couldn't get the questions");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("The file is not formatted properly, couldn't get the questions");
        }

    }

    public boolean isValidExamName(String examName) {
        for (Iterator<Question> iterator = questionRecords.iterator(); iterator.hasNext();) {
            Question question = iterator.next();
            if (question.getExamName().equalsIgnoreCase(examName)) {
                return false;
            }
        }
        return true;
    }

    public void findExam(String examName) {
        HashSet<Question> questions = new HashSet<>();
        String courseCode = null;
        for (Iterator<Question> iterator = questionRecords.iterator(); iterator.hasNext();) {
            Question q = iterator.next();
            if (q.getExamName().equalsIgnoreCase(examName)) {
                questions.add(q);
                if (courseCode == null) {
                    courseCode = q.getCourseCode();
                }
            }
        }
        if (courseCode != null) {
            Exam exam = new Exam(courseCode, examName);
            exam.setQuestions(questions);
            this.exam = exam;
            setChanged();
            notifyObservers(this.exam);
            this.nextQuestion();
        } else {
            this.exam = null;
            setChanged();
            notifyObservers(this.exam);
        }

    }

    public int getCourseMaxScore(String course) {
        int maxScore = 0;
        for (Iterator<Question> iterator = questionRecords.iterator(); iterator.hasNext();) {
            Question q = iterator.next();
            if (q.getCourseCode().equalsIgnoreCase(course)) {
                maxScore++;
            }
        }
        return maxScore;
    }

    public ArrayList<Exam> getExams(String course) {
        ArrayList<Exam> exams = new ArrayList<>();
        for (Iterator<Question> iterator = questionRecords.iterator(); iterator.hasNext();) {
            Question q = iterator.next();
            if (q.getCourseCode().equalsIgnoreCase(course)) {
                Exam exam = new Exam(course, q.getExamName());
                int examIndex = exams.indexOf(exam);
                if (examIndex != -1) {
                    exams.get(examIndex).addQuestion(q);
                } else {
                    exam.addQuestion(q);
                    exams.add(exam);
                }
            }

        }
        return exams;
    }

    public void dbsetup() {
        this.loadQuestions();
    }

    public void updateScore(String userAnswer) {
        boolean answerIsCorrect = questionRecords.get(this.currentQuestionIndex).answerIsCorrect(userAnswer);
        if (answerIsCorrect) {
            score++;
        }
        this.nextQuestion();
    }

    public void quitGame() {
        if (!canQuitSafely) {
            this.setChanged();
            notifyObservers(this.score);
        } else {
            System.exit(0);
        }
        this.canQuitSafely = true;
    }

    public int getScore() {
        return this.score;
    }

    public void login(String username, String password) {
        setChanged();
        notifyObservers(true);
        nextQuestion();
    }

}
