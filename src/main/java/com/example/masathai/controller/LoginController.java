package com.example.masathai.controller;

import com.example.masathai.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController {
    @FXML
    private AnchorPane loginPage;
    @FXML
    private PasswordField password;
    @FXML
    private TextField userName;

    public void initialize(){
        User.loadDataFromCsv();
    }

    @FXML
    public void signIn() throws IOException {
        if (User.validateLogin(userName.getText(), password.getText())){
            getToRegistration();
        }else {
            System.out.println("Error Logging In.");
        }
    }


    public void getToRegistration() throws IOException {
        new SceneController(loginPage, "registration.fxml");
    }
}