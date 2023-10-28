package start;

import controller.ChooseExamController;
import controller.NextQuestionController;
import controller.QuitController;
import model.Model;
import view.ExamCodeView;
import view.QuestionView;

public class QuizMain {

    public static void main(String[] args) {
        Model model = new Model();
        QuestionView view = new QuestionView();
        ExamCodeView examCodeView = new ExamCodeView();
        NextQuestionController nextQuestionController = new NextQuestionController();
        ChooseExamController chooseExamController = new ChooseExamController();
        QuitController quitController = new QuitController();

        model.addObserver(view);
        model.addObserver(examCodeView);

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

        examCodeView.setVisible(true);
    }

}
