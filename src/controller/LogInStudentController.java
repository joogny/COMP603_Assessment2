/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;
import model.UserType;
import view.LogInView;

/**
 *
 * @author julien
 */
public class LogInStudentController implements ActionListener {

    Model model;
    LogInView view;

    public void addModel(Model m) {
        this.model = m;
    }

    public void addView(LogInView v) {
        this.view = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = view.getUserNameInput().getText();
        if (username.length() == 0) {
            view.getEmptyInput().setVisible(true);
        } else {
            model.login(username, UserType.STUDENT);
        }
    }

}
