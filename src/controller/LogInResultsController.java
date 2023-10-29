/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ActionType;
import model.Model;
import view.LogInView;

/**
 *
 * @author julien
 */
public class LogInResultsController implements ActionListener {

    Model model;
    LogInView view;

    public void addModel(Model m) {
        this.model = m;
    }

    public void addView(LogInView v) {
        this.view = v;
    }

    @Override
    //login in model to see results
    public void actionPerformed(ActionEvent e) {
        String username = view.getUserNameInput().getText();
        if (username.length() == 0) {
            view.getEmptyInput().setVisible(true);
        } else {
            model.login(username, ActionType.RESULTS);
        }
    }

}
