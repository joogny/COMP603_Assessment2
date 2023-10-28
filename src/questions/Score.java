package questions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Julien
 */
public class Score {

    private String studentId;
    private int score;
    private String examCode;

    public Score(String studentId, int score, String examCode) {
        this.studentId = studentId;
        this.score = score;
        this.examCode = examCode;
    }

    public String formatForFile() {
        return this.examCode + "/" + this.studentId + "/" + this.score;
    }

    public int getScore() {
        return score;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getExamCode() {
        return examCode;
    }

}
