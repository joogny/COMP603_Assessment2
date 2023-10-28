package start;

import controller.ChooseExamController;
import controller.LogInStudentController;
import controller.LogInTeacherController;
import controller.NextQuestionController;
import controller.QuitController;
import model.Model;
import view.ExamCodeView;
import view.LogInView;
import view.QuestionView;

public class QuizMain {

    public static void main(String[] args) {
        Model model = new Model();
        QuestionView view = new QuestionView();
        ExamCodeView examCodeView = new ExamCodeView();
        LogInView logInView = new LogInView();

        NextQuestionController nextQuestionController = new NextQuestionController();
        ChooseExamController chooseExamController = new ChooseExamController();
        QuitController quitController = new QuitController();
        LogInStudentController logInStudentController = new LogInStudentController();
        LogInTeacherController logInTeacherController = new LogInTeacherController();

        logInView.addTeacherLoginController(logInTeacherController);
        logInView.addStudentLoginController(logInStudentController);

        model.addObserver(view);
        model.addObserver(examCodeView);
        model.addObserver(logInView);

        logInTeacherController.addModel(model);
        logInTeacherController.addView(logInView);
        logInStudentController.addView(logInView);
        logInStudentController.addModel(model);

        nextQuestionController.addModel(model);
        nextQuestionController.addView(view);
        nextQuestionController.initModel();

        chooseExamController.addModel(model);
        chooseExamController.addView(examCodeView);

        quitController.addModel(model);
        quitController.addView(view);

        view.addNextButtonController(nextQuestionController);
        examCodeView.addChooseExamController(chooseExamController);
        view.addQuitButtonController(quitController);

        logInView.setVisible(true);
    }

}
