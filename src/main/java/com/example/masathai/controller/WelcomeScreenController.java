package com.example.masathai.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.example.masathai.model.User.currentUser;

public class WelcomeScreenController {
    @FXML
    private Label fullName;

    @FXML
    private AnchorPane welcomeScreen;


    public void initialize(){
        fullName.setText(currentUser.getFullName());
    }


    @FXML
    public void goToUserDetails() throws IOException {
        new SceneController(welcomeScreen, "details-view.fxml");
    }

    @FXML
    public void logout() throws IOException {
        currentUser = null;
        new SceneController(welcomeScreen, "login.fxml");
    }

    @FXML
    public void getToResultSummary() throws IOException {
        new SceneController(welcomeScreen, "result-summary.fxml");

    }
}
