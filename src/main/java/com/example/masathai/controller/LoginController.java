    package com.example.masathai.controller;

    import com.example.masathai.model.User;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.PasswordField;
    import javafx.scene.control.TextField;
    import javafx.scene.layout.AnchorPane;

    import java.io.IOException;

    public class LoginController {
        @FXML
        private AnchorPane loginPage;
        @FXML
        private Label errorText;
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
                getToWelcomeScreen();
            }else {
                errorText.setText("Error: Incorrect credentials");
                System.out.println("Error Logging In.");
            }
        }


        public void getToRegistration() throws IOException {
            new SceneController(loginPage, "registration.fxml");
        }
        public void getToWelcomeScreen() throws IOException {
            new SceneController(loginPage, "welcome-view.fxml");
        }

    }