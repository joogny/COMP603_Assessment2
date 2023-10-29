package view;

import controller.NextQuestionController;
import controller.QuitController;
import controller.SaveScoreController;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.Model;
import questions.Exam;
import questions.Question;

public class QuestionView extends JFrame implements Observer {

    private JPanel questionPanel = new JPanel();

    private JLabel question = new JLabel();
    private JLabel score = new JLabel();

    private JButton nextButton = new JButton("Next");
    private JButton quitButton = new JButton("Quit");

    private JButton saveAndQuitButton = new JButton("Save score and quit");

    private JTextField calcSolution = new JTextField(10);

    public JTextField getCalcSolution() {
        return calcSolution;
    }

    public QuestionView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);

    }

    void startQuiz() {
        questionPanel.add(question);
        questionPanel.add(calcSolution);
        questionPanel.add(saveAndQuitButton);
        saveAndQuitButton.setVisible(false);
        questionPanel.add(nextButton);
        questionPanel.add(quitButton);

        this.getContentPane().removeAll();
        questionPanel.setVisible(true);
        this.add(questionPanel);
        this.revalidate();
        this.repaint();

    }

    public void addNextButtonController(NextQuestionController c) {
        nextButton.addActionListener(c);
    }

    public void addQuitButtonController(QuitController c) {
        quitButton.addActionListener(c);
    }

    public void addSaveQuitButtonController(SaveScoreController c) {
        this.saveAndQuitButton.addActionListener(c);
    }

    private void displayQuestion(Question q) {
        this.question.setText(q.toString() + "");
        this.calcSolution.setText("");
        this.questionPanel.repaint();
    }

    @Override
    public void update(Observable o, Object object) {
        if (o instanceof Model) {
            if (object instanceof Question) {
                Question q = (Question) object;
                this.displayQuestion(q);
            } else if (object instanceof Exam) {
                this.setVisible(true);
                this.startQuiz();
            } else if (object instanceof Integer) {
                this.displayScore((Integer) object);
            }
        }

    }

    private void displayScore(Integer score) {
        this.question.setText("You scored " + score);
        this.calcSolution.setVisible(false);
        this.nextButton.setVisible(false);
        this.saveAndQuitButton.setVisible(true);
        this.quitButton.setVisible(false);
        this.questionPanel.repaint();
    }
}
