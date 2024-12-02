package com.example.demo;

import java.util.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu extends Observable {

    private final Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene createMainMenuScene() {
        // Create the start button
        Button startButton = new Button("Start Game");
        startButton.setPrefWidth(200);  // Set a preferred width
        startButton.setPrefHeight(50);  // Set a preferred height

        // Use StackPane to center the button
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);  // Align the button at the center
        root.getChildren().add(startButton);

        // Create scene with explicit size
        Scene scene = new Scene(root, 800, 600);

        // Debug: Set the background color to see the layout
        root.setStyle("-fx-background-color: lightblue;");

        // Set the scene to the stage and make sure it's visible
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();

        // Set up the button click event to notify the controller
        startButton.setOnAction(event -> {
            // Mark that the state has changed and notify observers (Controller)
            setChanged();
            notifyObservers("com.example.demo.LevelOne");
        });

        return scene;
    }
}
