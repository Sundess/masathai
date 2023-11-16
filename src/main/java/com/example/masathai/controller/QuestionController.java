package com.example.masathai.controller;

import com.example.masathai.model.Question;
import com.example.masathai.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

import static com.example.masathai.model.User.currentUser;

public class QuestionController {
    @FXML
    private Label questionNoDisplay, questionTextDisplay, currentNo, fullName, gender;
    @FXML
    private RadioButton opt1, opt2, opt3, opt4;
    @FXML
    private ImageView optionImage1, optionImage2, optionImage3, optionImage4, qnImage;
    @FXML
    private ImageView country;
    @FXML
    private Button prevBtn, nextBtn;
    @FXML
    private AnchorPane questionAnchorPane;
    public static List<Question> questions;
    private int questionNoTracker = 0;

    // Initialize method called when the FXML is loaded
    @FXML
    private void initialize() {
        // Set user details and load questions
        fullName.setText(currentUser.getFullName());
        gender.setText(currentUser.getGender());
        String flag = User.getFlag(currentUser.getNationality());
        Image image = new Image(getClass().getResource(flag).toExternalForm());
        country.setImage(image);
        loadQuestionsFromCSV();
        loadQuestions();
    }

    // Load questions from CSV file
    private void loadQuestionsFromCSV() {
        questions = Question.loadQuestionsFromCSV();
    }

    // Load and display the current question
    private void loadQuestions() {
        // Display question number and set up UI components
        questionNoDisplay.setText("Question No." + (questionNoTracker + 1));
        if (questionNoTracker < questions.size()) {
            Question currentQuestion = questions.get(questionNoTracker);
            questionTextDisplay.setText(currentQuestion.getQuestionText());

            // Load question image if available
            if (currentQuestion.getImageName() != null) {
                // Load specific images based on the image name
                // (You may want to refactor this part for better maintenance)
                String image = currentQuestion.getImageName();
                if (image.equals("Marina Bay Sands")) {
                    // Load Marina Bay Sands image
                    Image image1 = new Image(getClass().getResource("/images/questionImages/marinaBaySands.jpg").toExternalForm());
                    qnImage.setImage(image1);
                } else if (image.equals("Wat Pho")) {
                    // Load Wat Pho image
                    Image image1 = new Image(getClass().getResource("/images/questionImages/watPho.jpeg").toExternalForm());
                    qnImage.setImage(image1);
                } else if (image.equals("Batu Cave")) {
                    // Load Batu Cave image
                    Image image1 = new Image(getClass().getResource("/images/questionImages/batuCave.jpg").toExternalForm());
                    qnImage.setImage(image1);
                }
            }

            // Set up options based on the question
            String[] options = currentQuestion.getOptions();
            if (options[0].equals("Malasiya Flag")) {
                // Load flag images for a specific question
                opt1.setText("");
                Image image1 = new Image(getClass().getResource("/images/flags/malasiya.png").toExternalForm());
                optionImage1.setImage(image1);
                opt2.setText("");
                Image image2 = new Image(getClass().getResource("/images/flags/singapore.jpg").toExternalForm());
                optionImage2.setImage(image2);
                opt3.setText("");
                Image image3 = new Image(getClass().getResource("/images/flags/thailand.jpg").toExternalForm());
                optionImage3.setImage(image3);
                opt4.setText("");
                Image image4 = new Image(getClass().getResource("/images/flags/england.jpg").toExternalForm());
                optionImage4.setImage(image4);
            } else {
                // Set text options if not a specific question
                opt1.setText(options[0]);
                opt2.setText(options[1]);
                opt3.setText(options[2]);
                opt4.setText(options[3]);
            }
        }

        // Set visibility and text for buttons
        prevBtn.setVisible(questionNoTracker != 0);
        nextBtn.setText(questionNoTracker == 19 ? "Submit" : "Next");
        currentNo.setText((questionNoTracker + 1) + "/20");
    }

    // Update user score and write to CSV
    private void updateScore() throws IOException {
        User.users.get(currentUser.getUsername()).score = Question.currentUserScore;
        User.writeAllUser();
    }

    // Handle next button click
    @FXML
    private void nextClick() throws IOException {
        // Record user answer and move to the next question or result page
        String answer = getAnswer();
        Question.userAnswers.set(questionNoTracker, answer);

        if (questionNoTracker == 19) {
            // Calculate total score, update user score, and navigate to result page
            Question.currentUserScore = getTotalScore();
            updateScore();
            goToResult();
            return;
        }

        // Clear fields, move to the next question, and update UI
        clearFields();
        if (questionNoTracker != questions.size()) {
            questionNoTracker++;
        }
        loadQuestions();
        answerTrackerHelper();
    }

    // Handle previous button click
    @FXML
    private void prevClick() {
        // Record user answer, move to the previous question, and update UI
        String answer = getAnswer();
        clearFields();
        Question.userAnswers.set(questionNoTracker, answer);
        if (questionNoTracker != 0) {
            questionNoTracker--;
        }
        loadQuestions();
        answerTrackerHelper();
    }

    // Retrieve user answer
    private String getAnswer() {
        // Handle specific questions with flag options
        if (questionNoTracker == 2 || questionNoTracker == 11 || questionNoTracker == 16) {
            if (opt1.isSelected()) {
                return "Malasiya Flag";
            } else if (opt2.isSelected()) {
                return "Thailand Flag";
            } else if (opt3.isSelected()) {
                return "Singapore Flag";
            } else if (opt4.isSelected()) {
                return "England Flag";
            } else {
                return null;
            }
        } else {
            // Return the selected option's text for other questions
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
    }

    // Clear selected fields
    private void clearFields() {
        opt1.setSelected(false);
        opt2.setSelected(false);
        opt3.setSelected(false);
        opt4.setSelected(false);

        optionImage1.setImage(null);
        optionImage2.setImage(null);
        optionImage3.setImage(null);
        optionImage4.setImage(null);
    }

    // Track and highlight the previously selected answer
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

    // Calculate and return the total score
    private int getTotalScore() {
        for (int i = 0; i < questions.size(); i++) {
            if (Question.userAnswers.get(i) != null && Question.userAnswers.get(i).equals(questions.get(i).getCorrectAnswer())) {
                Question.currentUserScore++;
            }
        }
        return Question.currentUserScore;
    }

    // Navigate to the result page
    public void goToResult() throws IOException {
        new SceneController(questionAnchorPane, "result-view.fxml");
    }
}
