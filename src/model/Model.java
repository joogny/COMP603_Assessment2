/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import questions.Exam;
import questions.Question;

/**
 *
 * @author Julien
 */
public class Model extends Observable {

    Connection conn = null;
    String QUESTION_TABLE_NAME = "Question";
    String STUDENTEXAM_TABLE_NAME = "StudentExam";

    String url = "jdbc:derby:QuestionDB;create=true";  //url of the DB host
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";   //your DB password

    private Exam exam;
    private int score;
    private boolean canQuitSafely = false;
    private String userName;
    private ActionType userType;

    public Model() {
        score = 0;
    }

    public void nextQuestion() {
        this.exam.nextQuestion();
        Question q = this.exam.getCurrentQuestion();
        this.setChanged();
        if (q == null) {
            this.canQuitSafely = true;
            notifyObservers(this.score);
        } else {
            notifyObservers(q);
        }
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        this.setChanged();
        notifyObservers(this.exam);
    }

    public Exam findExam(String examName) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT examName, question, answer FROM Question " + "WHERE examName = '" + examName + "'");
        Exam exam = new Exam(examName);
        boolean hasQuestions = false;
        while (rs.next()) {
            hasQuestions = true;
            String question = rs.getString("question");
            String answer = rs.getString("answer");
            exam.addQuestion(new Question(question, answer));
        }
        statement.close();
        if (!hasQuestions) {
            return null;
        }
        return exam;
    }

    public ResultSet getUserExamResult(String userName, String examCode) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT examName, id, score FROM StudentExam " + "WHERE id = '" + userName + "' AND examName ='" + examCode
                + "'");
        return rs;
    }

    public void startExam(String examName) {
        try {
            if (getUserExamResult(userName, examName).next()) {
                setExam(null);
                return;
            }
            Exam exam = findExam(examName);
            if (exam != null) {
                setExam(exam);
                this.nextQuestion();
            } else {
                setExam(null);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            System.err.println("Couldn't start the exam");
        }
    }

    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement = null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
            System.err.println("Couldn't access the list of tables");
        }
        return flag;
    }

    public void dbsetup() {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            setupQuestionTable(statement);
            setupStudentExamTable(statement);
            statement.close();
        } catch (Throwable e) {
            System.err.println("Couldn't connect to DB");
        }
    }

    public void setupQuestionTable(Statement statement) throws SQLException {

        if (!checkTableExisting(QUESTION_TABLE_NAME)) {
            statement.executeUpdate("CREATE TABLE " + QUESTION_TABLE_NAME + " (examName VARCHAR(12), question VARCHAR(25), answer VARCHAR(25))");
            statement.executeUpdate("INSERT INTO " + QUESTION_TABLE_NAME + " VALUES('exam_1','What is 2+2?','4'),('exam_1','What is 2+3?','5')");
        }
    }

    public void setupStudentExamTable(Statement statement) throws SQLException {

        if (!checkTableExisting(STUDENTEXAM_TABLE_NAME)) {
            statement.executeUpdate("CREATE TABLE " + STUDENTEXAM_TABLE_NAME + " (examName VARCHAR(12), id VARCHAR(12), score INT)");
            statement.executeUpdate("INSERT INTO " + STUDENTEXAM_TABLE_NAME + " VALUES('exam_1','23194961',2),('exam_1','23194962',0)");
        }
    }

    public void updateScore(String userAnswer) {
        boolean answerIsCorrect = exam.getCurrentQuestion().answerIsCorrect(userAnswer);
        if (answerIsCorrect) {
            score++;
        }
        this.nextQuestion();
    }

    public void stopGame() {
        if (!canQuitSafely) {
            this.setChanged();
            notifyObservers(this.score);
        } else {
            System.exit(0);
        }
        this.canQuitSafely = true;
    }

    public void saveScore() {
        saveScore(score, userName, this.exam.getExamCode());
        System.exit(0);
    }

    private void saveScore(int score, String id, String examCode) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO " + STUDENTEXAM_TABLE_NAME + " VALUES('" + examCode + "','" + id + "'," + score + ")");

        } catch (SQLException ex) {
            System.err.println("Couldn't save exam score");
        }
    }

    public int getScore() {
        return this.score;
    }

    public void login(String username, ActionType userType) {
        this.userName = username;
        this.userType = userType;
        setChanged();
        notifyObservers(userType);
    }

    public int getPreviousScore(String examCode) {
        ResultSet rs;
        int score = -1;
        try {
            rs = getUserExamResult(this.userName, examCode);
            if (rs.next()) {
                score = rs.getInt("score");
            }
        } catch (SQLException ex) {
            System.err.println("Couldn't get the previous user scores");
        }
        this.setChanged();
        notifyObservers(score);
        return score;
    }

}
