package com.example.masathai.controller;

import com.example.masathai.model.User;
import com.example.masathai.util.MathUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Arrays;

public class ResultSummaryController {

    @FXML
    private Label maxMark;
    @FXML
    private Label meanMark;
    @FXML
    private Label meedianMark;
    @FXML
    private Label minMark;
    @FXML
    private Label modeMark;
    @FXML
    private AnchorPane resultSummaryPage;
    @FXML
    private Label sdMark;
    @FXML
    private Label userAge;
    @FXML
    private Label userGender;
    @FXML
    private Label userName;
    @FXML
    private Label userPercentage;
    @FXML
    private Label userScore;
    @FXML
    private Label userStatus;
    @FXML
    private ChoiceBox studentDetail;

    // Initialize method called when the FXML is loaded
    public void initialize() {
        // Get all user scores
        int[] scores = User.getAllScoresArray();

        // Calculate statistical measures
        int max = Arrays.stream(scores).max().getAsInt();
        int min = Arrays.stream(scores).min().getAsInt();
        int mode = MathUtil.calculateMode(scores);
        int mean = MathUtil.calculateMean(scores);
        int median = MathUtil.calculateMedian(scores);
        int sd = MathUtil.calculateStandardDeviation(scores);

        // Set the calculated values to the corresponding labels
        maxMark.setText(String.valueOf(max));
        minMark.setText(String.valueOf(min));
        modeMark.setText(String.valueOf(mode));
        meanMark.setText(String.valueOf(mean));
        meedianMark.setText(String.valueOf(median));
        sdMark.setText(String.valueOf(sd));

        // Set up the ChoiceBox with all usernames
        String[] items = User.getAllUsernames();
        studentDetail.getItems().addAll(items);

        // Handle selection change in the ChoiceBox
        studentDetail.setOnAction(event -> {
            // Get selected username
            String data = studentDetail.getSelectionModel().getSelectedItem().toString();

            // Retrieve user details based on the selected username
            User selectedUser = User.users.get(data);
            userName.setText(selectedUser.getFullName());
            userAge.setText(String.valueOf(selectedUser.getAge()));
            userGender.setText(selectedUser.getGender());

            // Display user's score, percentage, and status
            userScore.setText(String.valueOf(selectedUser.getScore()));
            userPercentage.setText(MathUtil.getPercentage(selectedUser.getScore()) + "%");
            userStatus.setText(selectedUser.status());
        });
    }

    // Navigate to the welcome screen
    public void getToWelcomeScreen() throws IOException {
        new SceneController(resultSummaryPage, "welcome-view.fxml");
    }
}
