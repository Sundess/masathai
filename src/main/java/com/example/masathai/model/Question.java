package com.example.masathai.model;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Question {
    // Static variables to store user answers and current user score
    public static List<String> userAnswers = new ArrayList<>(19);
    public static int currentUserScore = 0;

    // Instance variables for a single question
    String questionText;
    String[] options;
    String correctAnswer;
    String imageName;

    // Constructor for a single question
    Question(String question, String[] options, String correctAnswer, String imageText) {
        this.questionText = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.imageName = imageText;

        // Initialize userAnswers list with null placeholders
        for (int i = 0; i < 19; i++) {
            userAnswers.add(null);
        }
    }

    // Load questions from a CSV file
    public static List<Question> loadQuestionsFromCSV() {
        List<Question> questions = new ArrayList<>();
        try {
            // Read CSV file and parse each line
            CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/resources/data/questions.csv")).withSkipLines(1).build();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                // Extract question details from the CSV line
                String questionText = line[0];
                String[] options = new String[4];
                options[0] = line[1];
                options[1] = line[2];
                options[2] = line[3];
                options[3] = line[4];
                String correctAnswer = line[5];

                // Check if an image is provided
                if (line.length == 6) {
                    questions.add(new Question(questionText, options, correctAnswer, null));
                } else {
                    String imageText = line[6];
                    questions.add(new Question(questionText, options, correctAnswer, imageText));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return questions;
    }

    // Getter methods for question details
    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getImageName() {
        return imageName;
    }
}
