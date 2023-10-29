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

    /**
     * set the next question and notify the view of the new question
     */
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

    /**
     * Find an exam with the examName
     *
     * @param examName name of the exam
     * @return null if exam isn't found, else the exam with all its questions
     * @throws SQLException
     */
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

    /**
     * get the score of a user on a specific exam
     *
     * @param userName name of the user
     * @param examCode code of the exam
     * @return resultSet of the scores
     * @throws SQLException
     */
    public ResultSet getUserExamResult(String userName, String examCode) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT examName, id, score FROM StudentExam " + "WHERE id = '" + userName + "' AND examName = '" + examCode
                + "'");
        return rs;
    }

    public void startExam(String examName) {
        try {
            boolean userHasDoneExam = getUserExamResult(userName, examName).next();
            if (userHasDoneExam) {
                //if user has already done the exam, view will display a message
                setExam(null);
                return;
            }
            Exam exam = findExam(examName);
            if (exam != null) {
                //if exam is found, display the next question
                setExam(exam);
                this.nextQuestion();
            } else {
                setExam(null);
            }
        } catch (SQLException ex) {
            System.err.println("Couldn't start the exam");
        }
    }

    /**
     * Check if a table already exist
     *
     * @param newTableName name of the table
     * @return true if table exist, false if it doesn't
     */
    public boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
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
            //statement.executeUpdate("DROP TABLE " + QUESTION_TABLE_NAME);
            //statement.executeUpdate("DROP TABLE " + STUDENTEXAM_TABLE_NAME);
            setupQuestionTable(statement);
            setupStudentExamTable(statement);
            statement.close();
        } catch (Throwable e) {
            System.err.println(e);
            System.err.println("Couldn't connect to DB");
        }
    }

    /**
     * Create Question table and add values
     *
     * @param statement
     * @throws SQLException
     */
    public void setupQuestionTable(Statement statement) throws SQLException {

        if (!checkTableExisting(QUESTION_TABLE_NAME)) {
            statement.executeUpdate("CREATE TABLE " + QUESTION_TABLE_NAME + " (examName VARCHAR(12), question VARCHAR(100), answer VARCHAR(25))");
            //math_exam
            statement.executeUpdate("INSERT INTO " + QUESTION_TABLE_NAME + " VALUES('math_exam','What is 2+2?','4'),('math_exam','What is 2+3?','5'),('math_exam','What is 2/2?','1'),('math_exam','What is 10*2?','20'),('math_exam','What is 5/2?','2.5')");
            //cs_exam
            statement.executeUpdate("INSERT INTO " + QUESTION_TABLE_NAME + " VALUES('cs_exam',' What is a collection of data items?','Array'),('cs_exam','What is a programming error causing the program to terminate abruptly?','Bug'),('cs_exam','What organizes code into reusable parts?','Function'),('cs_exam','What represents a condition with two outcomes?','Boolean')");
        }
    }

    /**
     * Create StudentExam table and add values
     *
     * @param statement
     * @throws SQLException
     */
    public void setupStudentExamTable(Statement statement) throws SQLException {

        if (!checkTableExisting(STUDENTEXAM_TABLE_NAME)) {
            statement.executeUpdate("CREATE TABLE " + STUDENTEXAM_TABLE_NAME + " (examName VARCHAR(12), id VARCHAR(12), score INT)");
            statement.executeUpdate("INSERT INTO " + STUDENTEXAM_TABLE_NAME + " VALUES('math_exam','Julien',5),('cs_exam','Julien',3)");
        }
    }

    /**
     * Update score depending on user answer and get next question
     *
     * @param userAnswer
     */
    public void updateScore(String userAnswer) {
        boolean answerIsCorrect = exam.getCurrentQuestion().answerIsCorrect(userAnswer);
        if (answerIsCorrect) {
            score++;
        }
        this.nextQuestion();
    }

    public void stopGame() {
        //user isn't allowed to quit the program in the middle of the exam, his current score will be displayed first
        if (!canQuitSafely) {
            this.setChanged();
            notifyObservers(this.score);
        } else {
            System.exit(0);
        }
        this.canQuitSafely = true;
    }

    /**
     * Save score and exit program
     */
    public void saveScore() {
        saveScore(score, userName, this.exam.getExamCode());
        System.exit(0);
    }

    /**
     * Insert exam result into the database
     *
     * @param score exam score
     * @param id student id
     * @param examCode
     */
    public void saveScore(int score, String id, String examCode) {
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

    /**
     * Get the previous score of current user on a specific exam or -1 if he
     * doesn't have a score
     *
     * @param examCode
     * @return
     */
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

    public void setScore(int score) {
        this.score = score;
    }

}
