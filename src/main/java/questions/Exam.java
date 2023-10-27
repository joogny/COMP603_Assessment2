/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package questions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import questions.Question;
import user.CUIScanner;

/**
 *
 * @author Julien
 */
public class Exam {

    private HashSet<Question> questions;
    private String courseCode;
    private String examName;

    public Exam(String courseCode, String examCode) {
        this.courseCode = courseCode;
        questions = new HashSet<>();
        this.examName = examCode;
    }

    public String getExamCode() {
        return this.examName;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public HashSet<Question> getQuestions() {
        return questions;
    }

    public String getCourseCode() {
        return courseCode;
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
