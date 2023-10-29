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
import java.util.logging.Level;
import java.util.logging.Logger;
import questions.Exam;
import questions.Question;

/**
 *
 * @author Julien
 */
public class Model extends Observable {

    Connection conn = null;

    String url = "jdbc:derby:QuestionDB;create=true";  //url of the DB host
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";   //your DB password

    private Exam exam;
    private int currentQuestionIndex;
    private int score;
    private boolean canQuitSafely = false;
    private String userName;
    private UserType userType;

    public Model() {
        score = 0;
        currentQuestionIndex = 0;
    }

    public void nextQuestion() {
        Question q = this.exam.getQuestion(currentQuestionIndex);
        this.currentQuestionIndex++;
        this.setChanged();
        if (q == null) {
            this.canQuitSafely = true;
            notifyObservers(this.score);
        } else {
            notifyObservers(q);
        }
    }

    public boolean isValidExamName(String examName) {
        //SQL check if question with this exam exist
        return true;
    }

    public void updateExam(Exam exam) {
        this.exam = exam;
        this.setChanged();
        notifyObservers(this.exam);
    }

    public void findExam(String examName) {
        //SQL = find exam with examName and StudentID!=currentID
        //find questions of exam
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT examName, id FROM StudentExam " + "WHERE id = '" + this.userName + "' AND examName ='" + examName
                    + "'");
            if (rs.next()) {
                System.out.println("Exam is already done by student");
                updateExam(null);
                return;
            }

            rs = statement.executeQuery("SELECT examName, question, answer FROM Question " + "WHERE examName = '" + examName + "'");
            Exam exam = new Exam(examName);
            boolean hasQuestions = false;
            while (rs.next()) {
                hasQuestions = true;
                String question = rs.getString("question");
                System.out.println(question);
                String answer = rs.getString("answer");
                exam.addQuestion(new Question(question, answer));
            }
            if (hasQuestions) {
                updateExam(exam);
                this.nextQuestion();
            } else {
                //display that exam doesnt exist
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
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
            System.err.println(e);
        }
    }

    public void setupQuestionTable(Statement statement) throws SQLException {
        String tableName = "Question";

        if (!checkTableExisting(tableName)) {
            statement.executeUpdate("CREATE TABLE " + tableName + " (examName VARCHAR(12), question VARCHAR(25), answer VARCHAR(25))");
            statement.executeUpdate("INSERT INTO " + tableName + " VALUES('exam_1','What is 2+2?','4'),('exam_1','What is 2+3?','5')");
        }
    }

    public void setupStudentExamTable(Statement statement) throws SQLException {
        String tableName = "StudentExam";

        if (!checkTableExisting(tableName)) {
            statement.executeUpdate("CREATE TABLE " + tableName + " (examName VARCHAR(12), id VARCHAR(12), score INT)");
            statement.executeUpdate("INSERT INTO " + tableName + " VALUES('exam_1','23194961',2),('exam_1','23194962',0)");
        }
    }

    public void updateScore(String userAnswer) {
        boolean answerIsCorrect = exam.getQuestion(this.currentQuestionIndex - 1).answerIsCorrect(userAnswer);
        if (answerIsCorrect) {
            score++;
        }
        this.nextQuestion();
    }

    public void quitGame() {
        if (!canQuitSafely) {
            this.setChanged();
            notifyObservers(this.score);
        } else {
            System.exit(0);
        }
        this.canQuitSafely = true;
    }

    public int getScore() {
        return this.score;
    }

    public void login(String username, UserType userType) {
        this.userName = username;
        this.userType = userType;
        setChanged();
        notifyObservers(userType);
    }

}
