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
        String userAnswer = view.getCalcSolution().getText();
        model.updateScore(userAnswer);
    }

    public void addModel(Model m) {
        this.model = m;
    }

    public void addView(QuestionView v) {
        this.view = v;
    }

    public void quitGame() {
        this.model.stopGame();
    }

}
