/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ChooseExamController;
import controller.LogInStudentController;
import controller.LogInTeacherController;
import controller.QuitController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Model;
import model.UserType;

/**
 *
 * @author julien
 */
public class LogInView extends JFrame implements Observer {

    private JPanel panel = new JPanel();

    private JLabel userNameLabel = new JLabel("AUT ID: ");
    private JTextField userNameInput = new JTextField(10);

    private JLabel emptyInput = new JLabel("Please input a name");

    private JButton quitButton = new JButton("Quit");
    private JButton studentLogIn = new JButton("Log in as student");
    private JButton teacherLogIn = new JButton("Log in as teacher");

    public LogInView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        panel.add(userNameLabel);
        panel.add(userNameInput);
        panel.add(studentLogIn);
        panel.add(teacherLogIn);
        panel.add(emptyInput);
        emptyInput.setVisible(false);
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(panel);

    }

    public void addTeacherLoginController(LogInTeacherController c) {
        this.teacherLogIn.addActionListener(c);
    }

    public void addStudentLoginController(LogInStudentController c) {
        this.studentLogIn.addActionListener(c);
    }

    public JTextField getUserNameInput() {
        return userNameInput;
    }

    public JLabel getEmptyInput() {
        return emptyInput;
    }

    public void addChooseExamController(ChooseExamController c) {
        studentLogIn.addActionListener(c);
    }

    public void addQuitButtonController(QuitController c) {
        quitButton.addActionListener(c);
    }

    @Override
    public void update(Observable o, Object object) {
        if (o instanceof Model) {
            if (object instanceof UserType) {
                this.setVisible(false);
            }
        }

    }
}
