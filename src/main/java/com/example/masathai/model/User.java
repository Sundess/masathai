package com.example.masathai.model;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class User {
    String fName;
    String lName;
    String gender;
    LocalDate dob;
    String nationality;
    String username;
    String password;
    int score = 0;

    public User(String fName, String lName, String gender, LocalDate dob, String nationality, String username, String password) {
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.dob = dob;
        this.nationality = nationality;
        this.username = username;
        this.password = password;
    }

    public User(String fName, String lName, String gender, LocalDate dob, String nationality, String username, String password, int score) {
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.dob = dob;
        this.nationality = nationality;
        this.username = username;
        this.password = password;
        this.score = score;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getFullName(){
        return fName + " " + lName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getNationality() {
        return nationality;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public static void saveDataToCsv(User user) throws IOException {

        File file = new File("src/main/resources/data/users.csv");
        try {
            FileWriter outputfile = new FileWriter(file,true);
            CSVWriter writer = new CSVWriter(outputfile);

            // add data to csv
            String[] data1 = {user.fName, user.lName, user.gender, String.valueOf(user.dob), user.nationality, user.username, user.password};
            writer.writeNext(data1);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
