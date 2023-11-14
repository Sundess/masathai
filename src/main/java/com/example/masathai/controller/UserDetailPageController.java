package com.example.masathai.controller;

import com.example.masathai.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    public void initialize(){
        fName.setText(currentUser.getfName());
        lName.setText(currentUser.getlName());
        age.setText(String.valueOf(currentUser.getAge()));
        gender.setText(currentUser.getGender());
        String flag = User.getFlag(currentUser.getNationality());
        Image image = new Image(getClass().getResource(flag).toExternalForm());
        nationality.setImage(image);
    }

    @FXML
    public void takeTest() throws IOException {
        new SceneController(detailsPage, "question-view.fxml");
    }

    @FXML
    void getToUserWelcomeScreen() throws IOException {
        new SceneController(detailsPage, "welcome-view.fxml");
    }
}
