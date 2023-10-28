/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import model.Model;
import questions.Exam;
import questions.Question;
import records.ScoreRecords;

/**
 *
 * @author Julien
 */
public class Lecturer extends User {

    @Override
    public void interactionWithCUI(Model qr, ScoreRecords sr, CUIScanner sc) {
        System.out.println();
        System.out.println("What do you want to do?");
        System.out.println("Type 1 to create a new exam");
        System.out.println("Type 2 to check an exam grades");
        System.out.println("You can type X anytime to exit");
        String value = sc.nextLine();
        String[] possibleAnswers = {"1", "2"};

        while (!sc.answerIsPossible(value, possibleAnswers)) {
            System.out.println("Please type a valid option: ");
            value = sc.nextLine();
        }

        switch (value) {
            case "1":
                Exam exam = createExam(sc, qr);
                for (Question q : exam.getQuestions()) {
                    qr.addQuestion(q);
                }
                qr.save();
                System.out.println("Exam is now saved with the name : " + exam.getExamCode());
                System.out.println("Your students can use this name to find it");
                break;
            case "2":
                findExamGrade(qr, sr, sc);
                break;
        }

        interactionWithCUI(qr, sr, sc);
    }

    public void findExamGrade(Model qr, ScoreRecords sr, CUIScanner sc) {
//        System.out.print("Enter the name of the exam you want to check the grades of: ");
//        String examName = sc.nextLine();
//        Exam exam = qr.findExam(examName);
//        while (exam == null) {
//            System.out.print("This exam doesn't exist, type another one: ");
//            examName = sc.nextLine();
//            exam = qr.findExam(examName);
//        }
//        System.out.println("Here are the scores of every student:");
//        ArrayList<Score> scores = sr.findScores(examName);
//        for (Iterator<Score> iterator = scores.iterator(); iterator.hasNext();) {
//            Score score = iterator.next();
//            System.out.println(score.getStudentId() + ":" + score.getScore());
//        }
    }

    @Override
    public void startCUI(Model qr, ScoreRecords sr, CUIScanner sc) {
        System.out.println("Welcome to the QnA application for lecturers!");
        interactionWithCUI(qr, sr, sc);
    }

    public Exam createExam(CUIScanner sc, Model qr) {
        System.out.print("Enter name of the exam: ");
        String examName = sc.nextLine();
        while (!qr.isValidExamName(examName)) {
            System.out.print("This exam name is already used. Please use another one: ");
            examName = sc.nextLine();
        }
        System.out.print("Enter course code of the exam(for example COMP603): ");
        String courseCode = sc.nextLine();
        Exam exam = new Exam(courseCode, examName);
        boolean isDone = false;
        System.out.println("You can now start adding questions to the exam!");
        while (!isDone) {
            exam.addQuestion(createQuestion(sc, exam));
            System.out.println("Type Y if you want to add another question");
            isDone = !sc.nextLine().equalsIgnoreCase("Y");
        }
        return exam;
    }

    public Question createQuestion(CUIScanner sc, Exam exam) {
        System.out.print("Enter question: ");
        String question = sc.nextLine();
        System.out.print("Enter answer: ");
        String answer = sc.nextLine();
        return new Question(question, answer, exam.getExamCode(), exam.getCourseCode());
    }

}
