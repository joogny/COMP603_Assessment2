/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Julien
 */
public class Question {

    protected String question;
    protected String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String toString() {
        return question;
    }

    public boolean answerIsCorrect(String userAnswer) {
        return userAnswer.equals(answer);
    }

}
