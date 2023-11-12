package com.example.masathai.model;

import com.example.masathai.util.Hash;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class User {
    String fName;
    String lName;
    String gender;
    LocalDate dob;
    String nationality;
    String username;
    String password;
    int score = 0;
    public static HashMap<String, User> users = new HashMap<>();

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
            FileWriter outputFile = new FileWriter(file,true);
            CSVWriter writer = new CSVWriter(outputFile);

            // add data to csv
            String[] data1 = {user.fName, user.lName, user.gender, String.valueOf(user.dob), user.nationality, user.username, user.password};
            writer.writeNext(data1);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void loadDataFromCsv(){
        try {
            CSVReader csvReader = new CSVReaderBuilder(new FileReader("src/main/resources/data/users.csv")).withSkipLines(1).build();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 7) {
                    User user = new User(line[0], line[1], line[2],LocalDate.parse(line[3]),line[4],line[5],line[6],line[7] != null ? Integer.parseInt(line[7]) : 0);
                    users.put(user.username, user);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        System.out.println(users);

    }

    public static boolean validateLogin(String username, String password){
        if (users.containsKey(username) && users.get(username).password.equals(Hash.getHashedValue(password))){
            return true;
        }else {
            return false;
        }
    }
}
