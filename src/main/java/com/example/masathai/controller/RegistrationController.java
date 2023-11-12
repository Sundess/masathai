package com.example.masathai.controller;

import com.example.masathai.model.User;
import com.example.masathai.util.Hash;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class RegistrationController {
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private DatePicker dob;
    @FXML
    private ComboBox<String> nationality;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField pass2;
    @FXML
    private AnchorPane registrationPage;
    @FXML
    private RadioButton opt1, opt2, opt3;
    @FXML
    private Label errorText;

    public void initialize(){
        String [] nations = {"Malaysia" , "Singapore", "Thailand"};
        nationality.getItems().addAll(nations);
    }

    public RegistrationController(){

    }
    public void onSubmit() throws IOException {
        String firstName = fName.getText();
        String lastName = lName.getText();
        String genderUser = getGender();
        LocalDate birthday = dob.getValue();
        String userNationality = nationality.getValue();
        String uname = userName.getText();
        String inputPass = pass.getText();
        String inputPass2 = pass2.getText();

        // Field Validation
        if(firstName.isEmpty() || lastName.isEmpty() || genderUser == null || birthday == null || nationality == null || uname.isEmpty() || inputPass.isEmpty() || inputPass2.isEmpty()) {
            errorText.setText("Error: Please enter all the details.");
            return;
        }
        if(!inputPass.equals(inputPass2)) {
            errorText.setText("Error: Passwords do not match");
            return;
        }
        if(userName.getText() == "sandesh") {
            return;
        }
        System.out.println(
                firstName + lastName + genderUser + birthday + userNationality + uname + inputPass
        );

        String hasedPass = Hash.getHashedValue(inputPass);
        User currentUser = new User(firstName, lastName, genderUser, birthday, userNationality, uname, hasedPass);
        errorText.setTextFill(Color.GREEN);
        User.saveDataToCsv(currentUser);
        errorText.setText("Registration Successful.");

    }


    private String getGender() {
        if (opt1.isSelected()) {
            return opt1.getText();
        } else if (opt2.isSelected()) {
            return opt2.getText();
        } else if (opt3.isSelected()) {
            return opt3.getText();
        } else {
            return null;
        }
    }

}
