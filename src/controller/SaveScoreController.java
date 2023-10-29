/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;
import view.QuestionView;

/**
 *
 * @author julien
 */
public class SaveScoreController implements ActionListener {

    Model model;
    QuestionView view;

    @Override
    public void actionPerformed(ActionEvent ae) {
        model.saveScore();
    }

    public void addModel(Model m) {
        System.out.println("Controller: adding model");
        this.model = m;
    }

    public void addView(QuestionView v) {
        System.out.println("Controller: adding view");
        this.view = v;
    }

}
