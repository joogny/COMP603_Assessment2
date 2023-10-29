package model;

import controller.ChooseExamController;
import controller.ExamResultsController;
import controller.LogInExamController;
import controller.LogInResultsController;
import controller.NextQuestionController;
import controller.QuitController;
import controller.SaveScoreController;
import view.ExamCodeView;
import view.LogInView;
import view.QuestionView;

public class Quiz {

    public static void main(String[] args) {
        Model model = new Model();
        QuestionView questionView = new QuestionView();
        ExamCodeView examCodeView = new ExamCodeView();
        LogInView logInView = new LogInView();

        SaveScoreController saveScoreController = new SaveScoreController();
        saveScoreController.addModel(model);
        saveScoreController.addView(questionView);
        questionView.addSaveQuitButtonController(saveScoreController);

        ExamResultsController examResultsController = new ExamResultsController();
        examResultsController.addView(examCodeView);
        examResultsController.addModel(model);
        examCodeView.addExamResultsController(examResultsController);

        NextQuestionController nextQuestionController = new NextQuestionController();
        ChooseExamController chooseExamController = new ChooseExamController();
        QuitController quitController = new QuitController();
        LogInExamController logInStudentController = new LogInExamController();
        LogInResultsController logInTeacherController = new LogInResultsController();

        logInView.addTeacherLoginController(logInTeacherController);
        logInView.addStudentLoginController(logInStudentController);

        model.addObserver(questionView);
        model.addObserver(examCodeView);
        model.addObserver(logInView);

        logInTeacherController.addModel(model);
        logInTeacherController.addView(logInView);
        logInStudentController.addView(logInView);
        logInStudentController.addModel(model);

        nextQuestionController.addModel(model);
        nextQuestionController.addView(questionView);
        nextQuestionController.initModel();

        chooseExamController.addModel(model);
        chooseExamController.addView(examCodeView);

        quitController.addModel(model);

        questionView.addNextButtonController(nextQuestionController);
        examCodeView.addChooseExamController(chooseExamController);
        questionView.addQuitButtonController(quitController);

        logInView.setVisible(true);
    }

}
