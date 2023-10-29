/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;
import view.ExamCodeView;

/**
 *
 * @author julien
 */
public class ExamResultsController implements ActionListener {

    Model model;
    ExamCodeView view;

    public void addModel(Model m) {
        this.model = m;
    }

    public void addView(ExamCodeView v) {
        this.view = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String examCode = view.getExamCodeInput().getText();
        model.getPreviousScore(examCode);
    }
}
