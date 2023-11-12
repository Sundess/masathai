package com.example.masathai.controller;

import com.example.masathai.model.Question;
import com.example.masathai.util.MathUtil;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ResultViewController {

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
    private TableColumn<TableData, Integer> num;
    @FXML
    private TableColumn<TableData, String> ansColumn;
    @FXML
    private TableColumn<TableData, String> correctAnsColumn;
    @FXML
    private TableColumn<TableData, String> questionColumn;


    public void initialize(){
        int percentage = MathUtil.getPercentage(Question.currentUserScore);
        scoreText.setText(Question.currentUserScore + "/20");
        scorePercentage.setText( percentage + "%");
        progressIndicator.setProgress((double) percentage /100);
        resultStatusText.setText(getText(percentage));
        addDataToTable();
    }

    private String getText(int percentage){
        if (percentage >= 50){
            return "You Passed.";
        }else {
            return "You Failed";        }
    }

    private void  addDataToTable(){
        int quesntionNoTacker = 1;
//        list.add(new TableData(1,"this is question", "",""));
//        list.add(new TableData(2,"this is question", "",""));
        for (Question question : QuestionController.questions){
            list.add(new TableData(quesntionNoTacker, question.getQuestionText(),question.getCorrectAnswer(),Question.userAnswers.get(quesntionNoTacker - 1)));
            quesntionNoTacker++;
        }
        System.out.printf(list.toString());
        num.setCellValueFactory(new PropertyValueFactory<TableData,Integer>("qn"));
        ansColumn.setCellValueFactory(new PropertyValueFactory<TableData,String>("userAns"));
        correctAnsColumn.setCellValueFactory(new PropertyValueFactory<TableData,String>("correctAns"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<TableData,String>("question"));
        resultTable.setItems(list);
    }

    ObservableList<TableData> list = FXCollections.observableArrayList();
    public class TableData {
        int qn;
        String question;
        String correctAns;
        String userAns;
        TableData(int qn,String question, String correctAns,String userAns){
            this.qn = qn;
            this.question = question;
            this.correctAns = correctAns;
            this.userAns = userAns;
        }

        public int getQn() {
            return qn;
        }

        public String getQuestion() {
            return question;
        }

        public String getCorrectAns() {
            return correctAns;
        }

        public String getUserAns() {
            return userAns;
        }
    }
}
