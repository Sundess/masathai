package com.example.masathai.controller;

import com.example.masathai.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.example.masathai.model.User.currentUser;

public class UserDetailPageController {

    @FXML
    private AnchorPane detailsPage;
    @FXML
    private Label age;
    @FXML
    private Label fName;
    @FXML
    private Label gender;
    @FXML
    private Label lName;
    @FXML
    private ImageView nationality;

    // Initialize method called when the FXML is loaded
    public void initialize() {
        // Set up user details in the UI components
        fName.setText(currentUser.getfName());
        lName.setText(currentUser.getlName());
        age.setText(String.valueOf(currentUser.getAge()));
        gender.setText(currentUser.getGender());

        // Load and set the user's nationality flag image
        String flag = User.getFlag(currentUser.getNationality());
        Image image = new Image(getClass().getResource(flag).toExternalForm());
        nationality.setImage(image);
    }

    // Handle button click to start the test
    @FXML
    public void takeTest() throws IOException {
        // Navigate to the question view
        new SceneController(detailsPage, "question-view.fxml");
    }

    // Handle button click to go back to the user's welcome screen
    @FXML
    void getToUserWelcomeScreen() throws IOException {
        // Navigate to the user's welcome view
        new SceneController(detailsPage, "welcome-view.fxml");
    }
}
