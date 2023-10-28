/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package questions;

import java.util.UUID;

/**
 *
 * @author Julien
 */
public class Question {

    protected String question;
    protected String answer;
    protected String courseCode;
    protected String examName;

    public String getExamName() {
        return examName;
    }

    public Question(String question, String answer, String examCode, String courseCode) {
        this.question = question;
        this.courseCode = courseCode;
        this.examName = examCode;
        this.answer = answer;
    }

    public String toString() {
        return question;
    }

    public boolean answerIsCorrect(String userAnswer) {
        return userAnswer.equals(answer);
    }

    public String formatForFile() {
        return courseCode + "/" + examName + "/" + question + ":" + answer;
    }

    public String getCourseCode() {
        return courseCode;
    }

}
