/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package start;

import user.CUIScanner;
import model.Model;
import records.ScoreRecords;
import user.Lecturer;
import user.Student;
import user.User;

/**
 *
 * @author Julien
 */
public class QandASystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Model qr = new Model();
        qr.loadQuestions();
        ScoreRecords sr = new ScoreRecords();
        sr.loadQuestions();
        CUIScanner sc = new CUIScanner();

        System.out.println("Welcome to the QnA system");

        System.out.println("Type 1 to log in as lecturer");
        System.out.println("Type 2 to log in as student");
        System.out.println("You can type X anytime to exit");

        String value = sc.nextLine();
        String[] possibleAnswers = {"1", "2"};

        while (!sc.answerIsPossible(value, possibleAnswers)) {
            System.out.print("Please type a valid option: ");
            value = sc.nextLine();
        }
        User user;
        switch (value) {
            case "1":
                user = new Lecturer();
                user.startCUI(qr, sr, sc);
                break;
            case "2":
                user = new Student();
                user.startCUI(qr, sr, sc);
                break;
        }

    }
}
