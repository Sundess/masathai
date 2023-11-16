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

    // Initialize method called when the FXML is loaded
    public void initialize() {
        // Set the user's full name in the UI component
        fullName.setText(currentUser.getFullName());
    }

    // Handle button click to navigate to user details
    @FXML
    public void goToUserDetails() throws IOException {
        // Navigate to the details view
        new SceneController(welcomeScreen, "details-view.fxml");
    }

    // Handle button click to log out
    @FXML
    public void logout() throws IOException {
        // Set current user to null and navigate to the login view
        currentUser = null;
        new SceneController(welcomeScreen, "login.fxml");
    }

    // Handle button click to navigate to result summary
    @FXML
    public void getToResultSummary() throws IOException {
        // Navigate to the result summary view
        new SceneController(welcomeScreen, "result-summary.fxml");
    }
}
