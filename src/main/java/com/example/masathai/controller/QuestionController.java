package com.example.masathai.controller;

import com.example.masathai.model.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    @FXML
    private Label questionNoDisplay, questionTextDisplay, currentNo;
    @FXML
    private RadioButton opt1, opt2, opt3, opt4;
    @FXML
    private Button prevBtn, nextBtn;

    private static List<Question> questions;
    private final List<String> answers = new ArrayList<>(15);
    private int questionNoTracker = 0;
    private int score = 0;

    public QuestionController() {
        for (int i = 0; i < 15; i++) {
            answers.add(null); // Add null as a placeholder for answers
        }
    }

    @FXML
    private void initialize() {
        loadQuestionsFromCSV();
        loadQuestions();
    }

    private void loadQuestionsFromCSV() {
        questions = Question.loadQuestionsFromCSV();
    }

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
        nextBtn.setText(questionNoTracker == 14 ? "Submit" : "Next");
        currentNo.setText((questionNoTracker + 1) + "/15");
    }


    @FXML
    private void nextClick() {
        String answer = getAnswer();
        answers.set(questionNoTracker, answer);

        if (questionNoTracker == 14){
            score = getTotalScore();
            System.out.println("submiited");
            System.out.println(score);
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
        answers.set(questionNoTracker, answer);
        if (questionNoTracker != 0) {
            questionNoTracker--;
        }
        loadQuestions();
        answerTrackerHelper();
    }

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

    private void clearFields() {
        opt1.setSelected(false);
        opt2.setSelected(false);
        opt3.setSelected(false);
        opt4.setSelected(false);
    }

    private void answerTrackerHelper() {
        String[] options = questions.get(questionNoTracker).getOptions();
        if (answers.get(questionNoTracker) != null) {
            String answer = answers.get(questionNoTracker);
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

    private int getTotalScore(){
        for (int i = 0; i < questions.size(); i++ ){
            if(answers.get(i) != null && answers.get(i).equals(questions.get(i).getCorrectAnswer())){
                score ++;
            }
        }
        return score;
    }
}
