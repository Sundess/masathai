package com.example.masathai.model;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Question {
    String questionText;
    String[] options;
    String correctAnswer;
    String imageName;
    public static List<String> userAnswers = new ArrayList<>(19);
    public static int currentUserScore = 0;

    Question(String question,String[] options, String correctAnswer, String imageText){
        this.questionText = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.imageName = imageText;

        for (int i = 0; i < 19; i++) {
            userAnswers.add(null); // Add null as a placeholder for answers
        }
    }


    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getImageName(){return imageName;}

    public boolean isCorrectAnswer(String selectedAnswer) {
        return selectedAnswer.equals(correctAnswer);
    }

    public static List<Question> loadQuestionsFromCSV(){
        List<Question> questions = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/resources/data/questions.csv")).withSkipLines(1).build();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                String questionText = line[0];
                String[] options = new String[4];
                options[0] = line[1];
                options[1] = line[2];
                options[2] = line[3];
                options[3] = line[4];
                String correctAnswer = line[5];
                if (line.length == 6) {
                    questions.add(new Question(questionText, options, correctAnswer, null));
                }else {
                    String imageText = line[6];
                    questions.add(new Question(questionText, options, correctAnswer,imageText));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return questions;
    }


}
