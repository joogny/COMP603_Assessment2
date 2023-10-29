/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package questions;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Julien
 */
public class Exam {

    private HashSet<Question> questions;
    private String examName;
    private Question currentQuestion;
    private int currentQuestionIndex = 0;

    public Exam(String examCode) {
        questions = new HashSet<>();
        this.examName = examCode;
    }

    public String getExamCode() {
        return this.examName;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public HashSet<Question> getQuestions() {
        return questions;
    }

    public void nextQuestion() {
        int currentIndex = 0;
        for (Iterator<Question> iterator = questions.iterator(); iterator.hasNext();) {
            Question next = iterator.next();
            if (currentIndex == currentQuestionIndex) {
                this.currentQuestionIndex++;
                this.currentQuestion = next;
                return;
            }
            currentIndex++;
        }
        this.currentQuestion = null;
    }

    public void setQuestions(HashSet<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Exam exam2 = (Exam) o;

        return this.examName.equalsIgnoreCase(exam2.examName);
    }

}
