package view;

import controller.ChooseExamController;
import controller.QuitController;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.Model;
import model.UserType;
import questions.Exam;


/*
TODO:
DB
JUnit
Display results
Maybe files?
 */
public class ExamCodeView extends JFrame implements Observer {

    private JPanel examCodePanel = new JPanel();

    private JLabel examCodeLabel = new JLabel();
    private JTextField examCodeInput = new JTextField(10);

    private JLabel wrongExamCode = new JLabel("Exam doesn't exist");

    private JButton quitButton = new JButton("Quit");
    private JButton studentChooseExamButton = new JButton("Choose exam");

    private JButton teacherCreateExamButton = new JButton("Create exam");

    private UserType userType;

    public ExamCodeView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        examCodePanel.add(examCodeLabel);
        examCodePanel.add(examCodeInput);
        examCodePanel.add(studentChooseExamButton);
        examCodePanel.add(teacherCreateExamButton);
        examCodePanel.add(wrongExamCode);
        wrongExamCode.setVisible(false);
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
        studentChooseExamButton.addActionListener(c);
    }

    public void addQuitButtonController(QuitController c) {
        quitButton.addActionListener(c);
    }

    public void displayForUser(UserType userType) {
        this.userType = userType;
        if (userType == UserType.STUDENT) {
            studentChooseExamButton.setVisible(true);
            teacherCreateExamButton.setVisible(false);
            examCodeLabel.setText("Enter the name of the exam you want to start: ");
        } else {
            studentChooseExamButton.setVisible(false);
            teacherCreateExamButton.setVisible(true);
            examCodeLabel.setText("Enter the name of the exam you want to see the results of: ");

        }
        this.setVisible(true);

    }

    @Override
    public void update(Observable o, Object object) {
        if (o instanceof Model) {
            if (object instanceof UserType) {
                this.displayForUser((UserType) object);
            }

            if (object == null) {
                wrongExamCode.setVisible(true);
            } else if (object instanceof Exam) {
                this.setVisible(false);
            }
        }

    }
}
