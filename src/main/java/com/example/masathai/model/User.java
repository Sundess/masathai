package com.example.masathai.model;

import com.example.masathai.util.Hash;
import com.example.masathai.util.MathUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    // Static variables to store users and the current user
    public static HashMap<String, User> users = new HashMap<>();
    public static User currentUser;

    // Instance variables for user details
    public int score = 0;
    String fName;
    String lName;
    String gender;
    LocalDate dob;
    String nationality;
    String username;
    String password;

    // Constructor for creating a user
    public User(String fName, String lName, String gender, LocalDate dob, String nationality, String username, String password) {
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.dob = dob;
        this.nationality = nationality;
        this.username = username;
        this.password = password;
    }

    // Constructor with an additional parameter for score
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

    // Save user data to a CSV file
    public static void saveDataToCsv(User user) {
        users.put(user.getUsername(), user);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/data/users.ser", false))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load user data from a CSV file
    public static void loadDataFromCsv() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/data/users.ser"))) {
            users = (HashMap<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Write all users to the CSV file
    public static void writeAllUsersToCsv() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/data/users.ser", false))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validate user login
    public static boolean validateLogin(String username, String password) {
        if (users.containsKey(username) && users.get(username).password.equals(Hash.getHashedValue(password))) {
            currentUser = users.get(username);
            return true;
        } else {
            return false;
        }
    }

    // Get the flag image path based on nationality
    public static String getFlag(String nationality) {
        return switch (nationality) {
            case "Malaysia" -> "/images/flags/malasiya.png";
            case "Singapore" -> "/images/flags/singapore.jpg";
            default -> "/images/flags/thailand.jpg";
        };
    }

    // Get an array of all user scores
    public static int[] getAllScoresArray() {
        int[] scoresArray = new int[User.users.size()];

        int index = 0;
        for (User user : User.users.values()) {
            scoresArray[index] = user.getScore();
            index++;
        }

        return scoresArray;
    }

    // Get an array of all usernames (sorted by score)
    public static String[] getAllUsernames() {
        users = (HashMap<String, User>) sortUsersByScore(users);
        String[] userArray = new String[User.users.size()];

        int index = 0;
        for (User user : User.users.values()) {
            userArray[index] = user.getUsername();
            index++;
        }

        return userArray;
    }

    // Sort users by score in descending order
    public static Map<String, User> sortUsersByScore(HashMap<String, User> users) {
        List<Map.Entry<String, User>> userList = new ArrayList<>(users.entrySet());
        userList.sort(Comparator.comparingInt((Map.Entry<String, User> entry) -> entry.getValue().getScore()).reversed());
        return userList.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    // Getter methods for user details
    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getFullName() {
        return fName + " " + lName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public int getAge() {
        return MathUtil.getAge(dob);
    }

    public String getNationality() {
        return nationality;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    // Get user status based on the score
    public String status() {
        return this.score >= 10 ? "Passed" : "Failed";
    }
}
