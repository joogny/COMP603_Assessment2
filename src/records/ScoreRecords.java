/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package records;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import questions.Exam;
import questions.Question;
import questions.Score;

/**
 *
 * @author Julien
 */
public class ScoreRecords implements Records {

    private static final String FILEPATH = "./resources/scores.txt";

    private ArrayList<Score> scoreRecords;

    public ScoreRecords() {
        this.scoreRecords = new ArrayList<>();
    }

    @Override
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(FILEPATH));
            for (Iterator<Score> iterator = scoreRecords.iterator(); iterator.hasNext();) {
                Score score = iterator.next();
                pw.println(score.formatForFile());
            }
            pw.close();
            System.out.println("Score has been saved.");
        } catch (FileNotFoundException ex) {
            System.err.println("The score file is not here, score was not saved");
        }
    }

    @Override
    public void loadQuestions() {
        scoreRecords = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILEPATH);
            BufferedReader inputStream = new BufferedReader(fr);
            String line = null;
            while ((line = inputStream.readLine()) != null) {
                String[] splitString = line.split("/");

                String examCode = splitString[0];
                String studentId = splitString[1];
                String scoreString = splitString[2];
                try {
                    int score = Integer.parseInt(scoreString);
                    scoreRecords.add(new Score(studentId, score, examCode));
                } catch (NumberFormatException e) {
                    System.err.println("Error in file " + FILEPATH + ", " + scoreString + " is not a valid score");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found, couldn't get the scores");
        } catch (IOException ex) {
            System.err.println("Error reading from file " + FILEPATH + ", couldn't get the scores");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("The file is not formatted properly, couldn't get the scores");
        }

    }

    public void addScore(Score s) {
        this.scoreRecords.add(s);
    }

    public ArrayList<Score> findScores(String examName) {
        ArrayList<Score> scores = new ArrayList<>();
        for (Iterator<Score> iterator = this.scoreRecords.iterator(); iterator.hasNext();) {
            Score score = iterator.next();
            if (score.getExamCode().equalsIgnoreCase(examName)) {
                scores.add(score);
            }
        }
        return scores;
    }

    public int findExamsGrade(ArrayList<Exam> exams, String studentId) {
        int finalScore = 0;
        boolean hasGrades = false;
        for (Iterator<Score> scoreIterator = scoreRecords.iterator(); scoreIterator.hasNext();) {
            Score score = scoreIterator.next();
            if (score.getStudentId().equalsIgnoreCase(studentId)) {
                for (Iterator<Exam> examIterator = exams.iterator(); examIterator.hasNext();) {
                    Exam exam = examIterator.next();
                    if (exam.getExamCode().equals(score.getExamCode())) {

                        finalScore += score.getScore();
                        hasGrades = true;
                    }
                }
            }
        }
        if (hasGrades) {
            return finalScore;
        } else {
            return -1;
        }
    }

}
