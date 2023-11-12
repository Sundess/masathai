package com.example.masathai.controller;

import com.example.masathai.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class QuestionController {
    @FXML
    private Label questionNoDisplay, questionTextDisplay, currentNo;
    @FXML
    private RadioButton opt1, opt2, opt3, opt4;
    @FXML
    private Button prevBtn, nextBtn;
    @FXML
    private AnchorPane questionAnchorPane;
    public static List<Question> questions;
    private int questionNoTracker = 0;


    // Loads question and presents it to user
    @FXML
    private void initialize() {
        loadQuestionsFromCSV();
        loadQuestions();
    }

    private void loadQuestionsFromCSV() {
        questions = Question.loadQuestionsFromCSV();
    }

    // Presents question to user (loads into fxml)
    private void loadQuestions() {
        questionNoDisplay.setText("Question No." + (questionNoTracker + 1) );
        if (questionNoTracker < questions.size()) {
            Question currentQuestion = questions.get(questionNoTracker);
            questionTextDisplay.setText(currentQuestion.getQuestionText());

            String[] options = currentQuestion.getOptions();
            opt1.setText(options[0]);
            opt2.setText(options[1]);
            opt3.setText(options[2]);
            opt4.setText(options[3]);
        }

        prevBtn.setVisible(questionNoTracker != 0);
        nextBtn.setText(questionNoTracker == 19 ? "Submit" : "Next");
        currentNo.setText((questionNoTracker + 1) + "/20");
    }


    @FXML
    private void nextClick() throws IOException {
        String answer = getAnswer();
        Question.userAnswers.set(questionNoTracker, answer);

        if (questionNoTracker == 19){
            Question.currentUserScore = getTotalScore();
            System.out.println("submiited");
            System.out.println(Question.currentUserScore);
            goToResult();
            return;
        }

        clearFields();
        if (questionNoTracker != questions.size()) {
            questionNoTracker++;
        }
        loadQuestions();
        answerTrackerHelper();

    }

    @FXML
    private void prevClick() {
        String answer = getAnswer();
        Question.userAnswers.set(questionNoTracker, answer);
        if (questionNoTracker != 0) {
            questionNoTracker--;
        }
        loadQuestions();
        answerTrackerHelper();
    }

    // used to retrieve user answer.
    private String getAnswer() {
        if (opt1.isSelected()) {
            return opt1.getText();
        } else if (opt2.isSelected()) {
            return opt2.getText();
        } else if (opt3.isSelected()) {
            return opt3.getText();
        } else if (opt4.isSelected()) {
            return opt4.getText();
        } else {
            return null;
        }
    }

    // clear options filed
    private void clearFields() {
        opt1.setSelected(false);
        opt2.setSelected(false);
        opt3.setSelected(false);
        opt4.setSelected(false);
    }

    // helps track previous answer for user
    private void answerTrackerHelper() {
        String[] options = questions.get(questionNoTracker).getOptions();
        if (Question.userAnswers.get(questionNoTracker) != null) {
            String answer = Question.userAnswers.get(questionNoTracker);
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(answer)) {
                    switch (i) {
                        case 0:
                            opt1.setSelected(true);
                            break;
                        case 1:
                            opt2.setSelected(true);
                            break;
                        case 2:
                            opt3.setSelected(true);
                            break;
                        case 3:
                            opt4.setSelected(true);
                            break;
                    }
                }
            }
        }
    }

    // Get total score
    private int getTotalScore(){
        for (int i = 0; i < questions.size(); i++ ){
            if(Question.userAnswers.get(i) != null && Question.userAnswers.get(i).equals(questions.get(i).getCorrectAnswer())){
                Question.currentUserScore ++;
            }
        }
        return Question.currentUserScore;
    }

    public void goToResult() throws IOException {
       new SceneController(questionAnchorPane, "result-view.fxml");
    }
}
