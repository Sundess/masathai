package com.example.masathai.controller;

import com.example.masathai.model.User;
import com.example.masathai.util.MathUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    public void initialize() {
        int [] scores = User.getAllScoresArray();

        int max = Arrays.stream(scores).max().getAsInt();
        int min = Arrays.stream(scores).min().getAsInt();
        int mode = MathUtil.calculateMode(scores);

        int mean = MathUtil.calculateMean(scores);
        int median = MathUtil.calculateMedian(scores);
        int sd = MathUtil.calculateStandardDeviation(scores);

        maxMark.setText(String.valueOf(max));
        minMark.setText(String.valueOf(min));
        modeMark.setText(String.valueOf(mode));
        meanMark.setText(String.valueOf(mean));
        meedianMark.setText(String.valueOf(median));
        sdMark.setText(String.valueOf(sd));

        String[] items =User.getAllUsernames();
        studentDetail.getItems().addAll(items);

        studentDetail.setOnAction(event -> {
            String data = studentDetail.getSelectionModel().getSelectedItem().toString();

            User selectedUser = User.users.get(data);
            userName.setText(selectedUser.getFullName());
            userAge.setText(String.valueOf(selectedUser.getAge()));
            userGender.setText(selectedUser.getGender());

            userScore.setText(String.valueOf(selectedUser.getScore()));
            userPercentage.setText(MathUtil.getPercentage(selectedUser.getScore()) + "%");
            userStatus.setText(selectedUser.status());
        });

    }


    public void getToWelcomeScreen() throws IOException {
        new SceneController(resultSummaryPage, "welcome-view.fxml");
    }

}
