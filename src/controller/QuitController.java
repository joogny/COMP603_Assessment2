package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;
import view.QuestionView;

/**
 *
 * @author julien
 */
public class QuitController implements ActionListener {

    Model model;
    QuestionView view;

    @Override
    public void actionPerformed(ActionEvent ae) {

        model.stopGame();
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
