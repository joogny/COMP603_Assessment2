/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import records.ScoreRecords;
import java.util.ArrayList;
import questions.Exam;
import records.QuestionRecords;
import java.util.Iterator;
import questions.Question;
import questions.Score;

/**
 *
 * @author Julien
 */
public class Student extends User {

    private String id;

    @Override
    public void startCUI(QuestionRecords qr, ScoreRecords sr, CUIScanner sc) {
        System.out.println("Welcome to the QnA application for students!");
        System.out.print("Please enter your student id: ");
        this.id = sc.nextLine();
        interactionWithCUI(qr, sr, sc);
    }

    @Override
    public void interactionWithCUI(QuestionRecords qr, ScoreRecords sr, CUIScanner sc) {
        System.out.println();
        System.out.println("What do you want to do?");
        System.out.println("Type 1 to pass an exam");
        System.out.println("Type 2 to check a course grade");
        System.out.println("You can type X anytime to exit");

        String value = sc.nextLine();
        String[] possibleAnswers = {"1", "2"};

        while (!sc.answerIsPossible(value, possibleAnswers)) {
            System.out.print("Please type a valid option: ");
            value = sc.nextLine();
        }

        switch (value) {
            case "1": //pass an exam
                Score score = passExam(sc, findExam(sc, qr, sr));
                sr.addScore(score);
                sr.save();
                break;
            case "2": //check grade for a specific course
                this.findCourseGrade(sc, qr, sr);
                break;
        }

        interactionWithCUI(qr, sr, sc);
    }

    private void findCourseGrade(CUIScanner sc, QuestionRecords qr, ScoreRecords sr) {
        System.out.print("Enter the code of the course: ");
        String courseCode = sc.nextLine();
        ArrayList<Exam> examList = qr.getExams(courseCode);
        if (examList.isEmpty()) {
            System.out.println("There aren't any exams for this course yet");
        } else {
            int score = sr.findExamsGrade(examList, this.id);
            if (score == -1) {
                System.out.println("You haven't done any exam for this course yet");
            } else {
                System.out.println("Your score is " + score + "/" + qr.getCourseMaxScore(courseCode));
            }
        }

    }

    private Score passExam(CUIScanner sc, Exam exam) {
        int score = 0;
        System.out.println("Exam is now starting!");
        int index = 1;
        for (Iterator<Question> iterator = exam.getQuestions().iterator(); iterator.hasNext();) {
            Question q = iterator.next();
            System.out.print(index + ". " + q.getDisplayedQuestion() + " ");
            String answer = sc.nextLine();
            if (q.answerIsCorrect(answer)) {
                score++;
                System.out.println("Correct answer");
            } else {
                System.out.println("Wrong answer");
            }

        }
        System.out.println("You scored " + score);
        return new Score(this.id, score, exam.getExamCode());
    }

    //exam needs to exist and not be done by the student already
    private Exam findExam(CUIScanner sc, QuestionRecords qr, ScoreRecords sr) {

        Exam exam = null;
        boolean foundExam = false;
        boolean examIsNotDone = false;
        while (!(foundExam && examIsNotDone)) {
            System.out.print("Please enter the name of the exam: ");
            String examName = sc.nextLine();
            foundExam = false;
            examIsNotDone = false;
            exam = qr.findExam(examName);
            if (exam != null) {
                foundExam = true;
                ArrayList<Exam> exams = new ArrayList<>();
                exams.add(exam);
                if (sr.findExamsGrade(exams, this.id) != -1) {
                    System.out.println("You've already done this exam.");
                } else {
                    examIsNotDone = true;
                }
            } else {
                System.out.println("This exam doesn't exist, please type another exam name.");
            }
        }
        return exam;
    }

}
