package view;

import controller.ChooseExamController;
import controller.ExamResultsController;
import controller.QuitController;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.ActionType;
import model.Model;
import questions.Exam;


/*
TODO:
JUnit
Display results
Maybe files?
 */
public class ExamCodeView extends JFrame implements Observer {

    private JPanel examCodePanel = new JPanel();

    private JLabel examCodeLabel = new JLabel();
    private JTextField examCodeInput = new JTextField(10);

    private JLabel infoLabel = new JLabel("Exam doesn't exist or you have already done it");

    private JButton quitButton = new JButton("Quit");
    private JButton passExamButton = new JButton("Pass exam");

    private JButton seeExamResultsButton = new JButton("See exam results");

    private ActionType userType;

    public ExamCodeView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        examCodePanel.add(examCodeLabel);
        examCodePanel.add(examCodeInput);
        examCodePanel.add(passExamButton);
        examCodePanel.add(seeExamResultsButton);
        examCodePanel.add(infoLabel);
        infoLabel.setVisible(false);
        this.add(examCodePanel);

    }

    public JTextField getExamCodeInput() {
        return examCodeInput;
    }

    public void addChooseExamController(ChooseExamController c) {
        passExamButton.addActionListener(c);
    }

    public void addQuitButtonController(QuitController c) {
        quitButton.addActionListener(c);
    }

    public void addExamResultsController(ExamResultsController c) {
        seeExamResultsButton.addActionListener(c);
    }

    public void displayForUser(ActionType userType) {
        this.userType = userType;
        if (userType == ActionType.PASS_EXAM) {
            passExamButton.setVisible(true);
            seeExamResultsButton.setVisible(false);
            examCodeLabel.setText("Enter the name of the exam you want to start: ");
        } else {
            passExamButton.setVisible(false);
            seeExamResultsButton.setVisible(true);
            examCodeLabel.setText("Enter the name of the exam you want to see the results of: ");

        }
        this.setVisible(true);

    }

    @Override
    public void update(Observable o, Object object) {
        if (o instanceof Model) {
            if (object instanceof ActionType) {
                this.displayForUser((ActionType) object);
            }
            if (object instanceof Integer) {
                int score = (Integer) object;
                infoLabel.setVisible(true);
                if (score != -1) {
                    infoLabel.setText("You scored " + score);
                } else {
                    infoLabel.setText("You have no scores for this exam");
                }
            }
            if (object == null) {
                infoLabel.setVisible(true);
            } else if (object instanceof Exam) {
                this.setVisible(false);
            }
        }

    }
}
