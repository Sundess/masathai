package com.example.masathai.model;

import com.example.masathai.util.Hash;
import com.example.masathai.util.MathUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class User implements Serializable {
    String fName;
    String lName;
    String gender;
    LocalDate dob;
    String nationality;
    String username;
    String password;
    public int score = 0;
    public static HashMap<String, User> users = new HashMap<>();

    public static User currentUser;

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

    public int getAge(){
        return MathUtil.getAge(dob);
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

        public static void saveDataToCsv(User user){

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
                    if (line.length == 8) {
                        User user = new User(line[0], line[1], line[2],LocalDate.parse(line[3]),line[4],line[5],line[6],Integer.parseInt(line[7]));
                        users.put(user.username, user);
                    }
                    else {
                        User user = new User(line[0], line[1], line[2],LocalDate.parse(line[3]),line[4],line[5],line[6],0);
                        users.put(user.username, user);
                    }
                }
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }

        }

        public static void writeAllUsersToCsv() throws IOException {
            File file = new File("src/main/resources/data/users.csv");
            File file2 = new File("src/main/resources/data/test_result.csv");

            try (FileWriter outputFile = new FileWriter(file, false);
                 FileWriter outputFile2 = new FileWriter(file2, false)) {

                CSVWriter writer = new CSVWriter(outputFile);
                CSVWriter writer2 = new CSVWriter(outputFile2);


                // Write header
                String[] header = {"First Name", "Last Name", "Gender", "Date of Birth", "Nationality", "Username", "Password", "Score"};
                writer.writeNext(header);

                String[] header2 = {"userName", "userScore"};
                writer2.writeNext(header2);



                // Write data
                for (User user : users.values()) {
                    String[] userData = {user.fName, user.lName, user.gender, String.valueOf(user.dob), user.nationality, user.username, user.password, String.valueOf(user.score)};
                    writer.writeNext(userData);

                    String[] userScore = {user.username, String.valueOf(user.score)};
                    writer2.writeNext(userScore);
                }

                // Closing writer connection
                writer.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }


    public static boolean validateLogin(String username, String password){
        if (users.containsKey(username) && users.get(username).password.equals(Hash.getHashedValue(password))){
            currentUser = users.get(username);
            return true;
        }else {
            return false;
        }
    }

    public static String getFlag(String nationality){
        return switch (nationality) {
            case "Malaysia" -> "/images/flags/malasiya.png";
            case "Singapore" -> "/images/flags/singapore.jpg";
            default -> "/images/flags/thailand.jpg";
        };
    }

    public static int[] getAllScoresArray() {
        int[] scoresArray = new int[User.users.size()];

        int index = 0;
        for (User user : User.users.values()) {
            scoresArray[index] = user.getScore();
            index++;
        }

        return scoresArray;
    }

    public static String[] getAllUsernames(){
        users = (HashMap<String, User>) sortUsersByScore(users);
        String[] userArray = new String[User.users.size()];

        int index = 0;
        for (User user : User.users.values()) {
            userArray[index] = user.getUsername();
            index++;
        }

        return userArray;
    }

    // Sort Data in descending order
    public static Map<String, User> sortUsersByScore(HashMap<String, User> users) {
        // Convert the HashMap entries to a List
        List<Map.Entry<String, User>> userList = new ArrayList<>(users.entrySet());

        // Sort the List based on User.score in descending order
        userList.sort(Comparator.comparingInt((Map.Entry<String, User> entry) -> entry.getValue().getScore()).reversed());

        // Create a new HashMap from the sorted List
        Map<String, User> sortedUsers = userList.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedUsers;
    }


    public String status(){
        if(this.score >= 10){
            return "Passed";
        }else {
            return "Failed";
        }
    }

}
