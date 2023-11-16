package com.example.masathai.controller;

import com.example.masathai.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController {
    // FXML annotations to inject components from the FXML file
    @FXML
    private AnchorPane loginPage;
    @FXML
    private Label errorText;
    @FXML
    private PasswordField password;
    @FXML
    private TextField userName;

    // Initialize method called when the FXML is loaded
    public void initialize() {
        // Load user data from CSV file
        User.loadDataFromCsv();
    }

    // Method called when the "Sign In" button is pressed
    @FXML
    public void signIn() throws IOException {
        // Validate user login credentials
        if (User.validateLogin(userName.getText(), password.getText())) {
            // If credentials are valid, navigate to the welcome screen
            getToWelcomeScreen();
        } else {
            // If credentials are invalid, show an error message
            errorText.setText("Error: Incorrect credentials");
            System.out.println("Error Logging In.");
        }
    }

    // Method to navigate to the registration page
    public void getToRegistration() throws IOException {
        new SceneController(loginPage, "registration.fxml");
    }

    // Method to navigate to the welcome screen
    public void getToWelcomeScreen() throws IOException {
        new SceneController(loginPage, "welcome-view.fxml");
    }
}
