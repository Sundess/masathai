package com.example.masathai.controller;

import com.example.masathai.model.Question;
import com.example.masathai.model.User;
import com.example.masathai.util.MathUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ResultViewController {

    // ObservableList to store data for the TableView
    ObservableList<TableData> list = FXCollections.observableArrayList();

    @FXML
    private ProgressBar progressIndicator;
    @FXML
    private Label resultStatusText;
    @FXML
    private Label scorePercentage;
    @FXML
    private Label scoreText;
    @FXML
    private Label usernameText;
    @FXML
    private TableView<TableData> resultTable;
    @FXML
    private AnchorPane resultPage;
    @FXML
    private TableColumn<TableData, Integer> num;
    @FXML
    private TableColumn<TableData, String> ansColumn;
    @FXML
    private TableColumn<TableData, String> correctAnsColumn;
    @FXML
    private TableColumn<TableData, String> questionColumn;

    // Initialize method called when the FXML is loaded
    public void initialize() {
        // Calculate the percentage and set the UI elements
        int percentage = MathUtil.getPercentage(Question.currentUserScore);
        usernameText.setText(User.currentUser.getUsername());
        scoreText.setText(Question.currentUserScore + "/20");
        scorePercentage.setText(percentage + "%");
        progressIndicator.setProgress((double) percentage / 100);
        resultStatusText.setText(getText(percentage));
        addDataToTable();
    }

    // Get the result status text based on the percentage
    private String getText(int percentage) {
        if (percentage >= 50) {
            return "You Passed.";
        } else {
            return "You Failed";
        }
    }

    // Add data to the TableView
    private void addDataToTable() {
        int questionNoTracker = 1;
        for (Question question : QuestionController.questions) {
            list.add(new TableData(questionNoTracker, question.getQuestionText(), question.getCorrectAnswer(), Question.userAnswers.get(questionNoTracker - 1)));
            questionNoTracker++;
        }

        // Set up the columns and link them to the TableData properties
        num.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("qn"));
        ansColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("userAns"));
        correctAnsColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("correctAns"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("question"));

        // Set the data to the TableView
        resultTable.setItems(list);
    }

    // Handle button click to navigate to another view
    @FXML
    void getToResultView2() throws IOException {
        new SceneController(resultPage, "result-summary.fxml");
    }

    // Inner class to represent data for the TableView
    public class TableData {
        int qn;
        String question;
        String correctAns;
        String userAns;

        TableData(int qn, String question, String correctAns, String userAns) {
            this.qn = qn;
            this.question = question;
            this.correctAns = correctAns;
            this.userAns = userAns;
        }
    }
}
