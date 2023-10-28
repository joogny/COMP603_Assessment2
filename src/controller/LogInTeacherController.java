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
public class LogInTeacherController implements ActionListener {

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
        model.login("", UserType.TEACHER);
    }

}
