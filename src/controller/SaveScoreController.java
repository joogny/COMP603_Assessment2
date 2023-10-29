/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;

/**
 *
 * @author julien
 */
public class SaveScoreController implements ActionListener {

    Model model;

    @Override
    public void actionPerformed(ActionEvent ae) {
        model.saveScore();
    }

    public void addModel(Model m) {
        this.model = m;
    }

}
