package com.example.demo.userinterface;

import com.example.demo.SoundManager;
import com.example.demo.activeactor.PlayerAircraft;
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

/**
 * The {@link PauseMenu} class represents a modal menu displayed when the game is paused.
 * It provides options to resume the game, restart the game, or return to the {@link MainMenu}.
 */
public class PauseMenu {

    /** The stage for the {@link PauseMenu}. */
    private Stage pauseStage;

    /** The scene for the active game. */
    private Scene gameScene;

    /** Callback function to resume the game. */
    private Runnable resumeCallback;

    /** Callback function to return to the {@link MainMenu}. */
    private Runnable mainMenuCallback;

    /** Callback function to restart the game. */
    private Runnable restartCallback;

    /**
     * Constructs a new {@link PauseMenu} with the specified game scene and callback functions.
     *
     * @param gameScene        the scene of the game that is paused.
     * @param resumeCallback   a {@link Runnable} to execute when the "Resume" button is clicked.
     * @param mainMenuCallback a {@link Runnable} to execute when the "End Game" button is clicked.
     * @param restartCallback  a {@link Runnable} to execute when the "Restart" button is clicked.
     */
    public PauseMenu(Scene gameScene, Runnable resumeCallback, Runnable mainMenuCallback, Runnable restartCallback) {
        this.gameScene = gameScene;
        this.resumeCallback = resumeCallback;
        this.mainMenuCallback = mainMenuCallback;
        this.restartCallback = restartCallback;

        pauseStage = new Stage();
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.setTitle("Game Paused");
        pauseStage.setOnCloseRequest(event -> resumeCallback.run());

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

        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 16px; -fx-min-width: 200px;");
        restartButton.setOnAction(e -> restartGame());

        Button endGameButton = new Button("End Game");
        endGameButton.setStyle("-fx-font-size: 16px; -fx-min-width: 200px;");
        endGameButton.setOnAction(e -> endGame());

        pauseLayout.getChildren().addAll(resumeButton, restartButton, endGameButton);

        Scene pauseScene = new Scene(pauseLayout, 300, 250);
        pauseStage.setScene(pauseScene);
    }

    /**
     * Displays the {@link PauseMenu} and stops the background sound.
     */
    public void show() {
        SoundManager.stopBackgroundSound();
        pauseStage.showAndWait();
    }

    /**
     * Resumes the game by executing the resume callback and closing the {@link PauseMenu}.
     */
    private void resumeGame() {
        SoundManager.playBackgroundSound();

        if (resumeCallback != null) {
            resumeCallback.run();
        }
        pauseStage.close();
    }

    /**
     * Restarts the game by executing the restart callback and closing the {@link PauseMenu}.
     */
    private void restartGame() {
        if (restartCallback != null) {
            restartCallback.run();
        }
        pauseStage.close();
    }

    /**
     * Ends the game by executing the {@link MainMenu} callback and closing the {@link PauseMenu}.
     */
    private void endGame() {
        if (mainMenuCallback != null) {
            mainMenuCallback.run();
        }
        pauseStage.close();
    }

    /**
     * Checks if the {@link PauseMenu} is currently being displayed.
     *
     * @return true if the {@link PauseMenu} is showing. false otherwise.
     */
    public boolean isShowing() {
        return pauseStage.isShowing();
    }
}
