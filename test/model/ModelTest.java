/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author julien
 */
public class ModelTest {

    Model model;

    public ModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        model = new Model();
        model.dbsetup();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of findExam method, of class Model.
     */
    @Test
    public void testFindExam() throws Exception {
        System.out.println("findExam");
        String examName = "math_exam";
        Exam result = model.findExam(examName);
        if (result == null) {
            fail("Couldn't find the exam");
        }
    }

    /**
     * Test of dbsetup method, of class Model.
     */
    @Test
    public void testDbsetup() {
        System.out.println("dbsetup");
        if (!(model.checkTableExisting(model.QUESTION_TABLE_NAME)
                || model.checkTableExisting(model.STUDENTEXAM_TABLE_NAME))) // TODO review the generated test code and remove the default call to fail.
        {
            fail("Tables are not properly created");
        }
    }

    /**
     * Test of getPreviousScore method, of class Model.
     */
    @Test
    public void testGetPreviousScoreRealValues() {
        System.out.println("getPreviousScore");
        String examCode = "math_exam";
        model.login("Julien", ActionType.RESULTS);
        int result = model.getPreviousScore(examCode);
        assertEquals(5, result);
    }

    /**
     * Test of getPreviousScore method, of class Model.
     */
    @Test
    public void testGetPreviousScoreFakeValues() {
        System.out.println("getPreviousScore");
        String examCode = "fakeTest";
        model.login("fakeuser", ActionType.RESULTS);
        int result = model.getPreviousScore(examCode);
        assertEquals(-1, result);
    }

    @Test
    public void testGetUserExamResultFakeValues() {
        System.out.println("getUserExamResult");
        try {
            ResultSet rs = model.getUserExamResult("fakeTest", "fakeUser");
            if (rs.next()) {
                fail("Result shouldn't exist");
            }
        } catch (SQLException ex) {
            fail("SQL exception when getting result");
        }
    }

    @Test
    public void testGetUserExamResultRealValues() {
        System.out.println("getUserExamResult");
        try {
            ResultSet rs = model.getUserExamResult("Julien", "math_exam");
            if (!rs.next()) {
                fail("Result should exist");
            }
        } catch (SQLException ex) {
            fail("SQL exception when getting result");
        }
    }

    @Test
    public void testSaveScore() {
        model.saveScore(10, "TEST_USER", "TEST_EXAM");
        model.login("TEST_USER", ActionType.RESULTS);
        int result = model.getPreviousScore("TEST_EXAM");
        assertEquals(10, result);

    }

}
