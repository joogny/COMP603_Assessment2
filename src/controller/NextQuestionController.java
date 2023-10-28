package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;
import view.QuestionView;

public class NextQuestionController implements ActionListener {

    Model model;
    QuestionView view;

    public NextQuestionController() {
    }

    public void initModel() {
        model.dbsetup();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("You clicked the next button");
        String userAnswer = view.getCalcSolution().getText();
        model.updateScore(userAnswer);
    }

    public void addModel(Model m) {
        System.out.println("Controller: adding model");
        this.model = m;
    }

    public void addView(QuestionView v) {
        System.out.println("Controller: adding view");
        this.view = v;
    }

    public void quitGame() {
        this.model.quitGame();
    }

}
