package view;

import controller.ChooseExamController;
import controller.QuitController;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.Model;
import questions.Exam;

public class ExamCodeView extends JFrame implements Observer {

    private JPanel examCodePanel = new JPanel();

    private JLabel examCodeLabel = new JLabel("Exam code: ");
    private JTextField examCodeInput = new JTextField(10);

    private JLabel wrongExamCode = new JLabel("Exam doesn't exist");

    private JButton quitButton = new JButton("Quit");
    private JButton chooseExamButton = new JButton("Choose exam");

    public ExamCodeView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        examCodePanel.add(examCodeLabel);
        examCodePanel.add(examCodeInput);
        examCodePanel.add(chooseExamButton);
        this.add(examCodePanel);

    }

    public JTextField getExamCodeInput() {
        return examCodeInput;
    }

    void quitGame(int score) {
        JPanel quitPanel = new JPanel();
        JLabel scoreLabel = new JLabel("Your score: " + score);
        quitPanel.add(scoreLabel);
        this.getContentPane().removeAll();
        //calcPanel.setVisible(true);
        this.add(quitPanel);
        this.revalidate();
        this.repaint();

    }

    public void addChooseExamController(ChooseExamController c) {
        chooseExamButton.addActionListener(c);
    }

    public void addQuitButtonController(QuitController c) {
        quitButton.addActionListener(c);
    }

    @Override
    public void update(Observable o, Object object) {
        if (o instanceof Model) {
            if (object == null) {
                examCodePanel.add(wrongExamCode);
            } else if (object instanceof Exam) {
                this.setVisible(false);
            }
        }

    }
}
