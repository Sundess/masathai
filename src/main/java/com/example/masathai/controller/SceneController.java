package com.example.masathai.controller;

import com.example.masathai.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class SceneController {
    // Constructor to switch scenes
    public SceneController(AnchorPane currentAnchorPane, String fxml) throws IOException {
        // Load the FXML file for the next scene
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));

        // Remove all existing children from the current anchor pane and set the new one
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }
}
