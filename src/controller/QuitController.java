package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;

/**
 *
 * @author julien
 */
public class QuitController implements ActionListener {

    Model model;

    @Override
    public void actionPerformed(ActionEvent ae) {
        model.stopGame();
    }

    public void addModel(Model m) {
        this.model = m;
    }

}
