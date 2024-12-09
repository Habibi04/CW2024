package com.example.demo;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;

public class PauseMenu {
    private Stage pauseStage;
    private Scene gameScene;
    private Runnable resumeCallback;
    private Runnable mainMenuCallback;

    public PauseMenu(Scene gameScene, Runnable resumeCallback, Runnable mainMenuCallback) {
        this.gameScene = gameScene;
        this.resumeCallback = resumeCallback;
        this.mainMenuCallback = mainMenuCallback;

        pauseStage = new Stage();
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.setTitle("Game Paused");

        VBox pauseLayout = new VBox(20);
        pauseLayout.setAlignment(Pos.CENTER);
        pauseLayout.setPadding(new Insets(20));
        pauseLayout.setBackground(new Background(new BackgroundFill(
                Color.DARKGRAY.deriveColor(0, 1, 1, 0.8),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        Button resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-font-size: 16px; -fx-min-width: 200px;");
        resumeButton.setOnAction(e -> resumeGame());

        Button endGameButton = new Button("End Game");
        endGameButton.setStyle("-fx-font-size: 16px; -fx-min-width: 200px;");
        endGameButton.setOnAction(e -> endGame());

        pauseLayout.getChildren().addAll(resumeButton, endGameButton);

        Scene pauseScene = new Scene(pauseLayout, 300, 250);
        pauseStage.setScene(pauseScene);
    }

    public void show() {
        pauseStage.showAndWait();
    }

    private void resumeGame() {
        if (resumeCallback != null) {
            resumeCallback.run();
        }
        pauseStage.close();
    }

    private void endGame() {
        if (mainMenuCallback != null) {
            mainMenuCallback.run();
        }
        pauseStage.close();
    }

    public boolean isShowing() {
        return pauseStage.isShowing();
    }
}
