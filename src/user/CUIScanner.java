/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import java.util.Scanner;

/**
 *
 * @author Julien
 */
public class CUIScanner {

    private Scanner sc = new Scanner(System.in);

    public Scanner getSc() {
        return sc;
    }

    public String nextLine() {
        boolean isDone = false;
        String nextLine = sc.nextLine();
        isDone = !(nextLine.contains("/") || nextLine.contains(":"));
        if (nextLine.equalsIgnoreCase("X")) {
            System.exit(0);
        }
        while (!isDone) {
            if (nextLine.equalsIgnoreCase("X")) {
                System.exit(0);
            }
            System.out.println("Please, do not use : or / in your input, try again: ");
            nextLine = sc.nextLine();

            isDone = !(nextLine.contains("/") || nextLine.contains(":"));
        }
        return nextLine;
    }

    public boolean answerIsPossible(String answer, String[] possibleAnswers) {
        for (String possibleAnswer : possibleAnswers) {
            if (possibleAnswer.equalsIgnoreCase(answer)) {
                return true;
            }
        }
        return false;
    }
}
