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
    private static final long serialVersionUID = 1L;
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
            users.put(user.getUsername(), user);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/data/users.ser", false))) {
                oos.writeObject(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void loadDataFromCsv(){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/data/users.ser"))) {
                users = (HashMap<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void writeAllUsersToCsv() throws IOException {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/data/users.ser", false))) {
                oos.writeObject(users);
            } catch (IOException e) {
                e.printStackTrace();
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
