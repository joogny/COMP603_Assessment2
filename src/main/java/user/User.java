/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import records.QuestionRecords;
import records.ScoreRecords;

/**
 *
 * @author Julien
 */
public abstract class User {

    public abstract void startCUI(QuestionRecords qr, ScoreRecords sr, CUIScanner sc);

    public abstract void interactionWithCUI(QuestionRecords qr, ScoreRecords sr, CUIScanner sc);

}
