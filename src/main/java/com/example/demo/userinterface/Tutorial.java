package com.example.demo.userinterface;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The {@link Tutorial} class represents the tutorial screen of the application,
 * providing instructions on how to play the game.
 */
public class Tutorial {

    /** The relative path to the background image resource for the {@link Tutorial}. */
    private static final String backgroundImageName = "/com/example/demo/images/background1.1.jpg";

    /** The width of the {@link Tutorial} screen. */
    private static final int SCREEN_WIDTH = 1300;

    /** The height of the {@link Tutorial} screen. */
    private static final int SCREEN_HEIGHT = 750;

    /** The {@link Stage} on which the {@link Tutorial} is displayed. */
    private final Stage stage;

    /**
     * Constructs a new {@link Tutorial} instance.
     *
     * @param stage the {@link Stage} on which the {@link Tutorial} is displayed.
     */
    public Tutorial(Stage stage) {
        this.stage = stage;
    }

    /**
     * Displays the {@link Tutorial} screen.
     */
    public void show() {
        Group tutorialLayout = new Group();
        tutorialLayout.setStyle("-fx-alignment: center; -fx-padding: 50; -fx-background-color: #000;");

        VBox contentLayout = new VBox(20);
        contentLayout.setAlignment(Pos.CENTER);

        // Load the background image
        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
        background.setViewOrder(1); // Ensure the background is behind other elements
        background.setFitWidth(SCREEN_WIDTH);
        background.setFitHeight(SCREEN_HEIGHT);
        tutorialLayout.getChildren().add(background);

        // Create instructions text with larger font size
        Text instructions = new Text("USE THE ARROW KEYS TO MOVE:\n" +
                "UP ARROW - MOVE UP\n" +
                "DOWN ARROW - MOVE DOWN\n" +
                "LEFT ARROW - MOVE LEFT\n" +
                "RIGHT ARROW - MOVE RIGHT\n" +
                "SPACE BAR - SHOOT ENEMIES");

        // Set the font size to make the text larger
        instructions.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        instructions.setTranslateX((double) SCREEN_WIDTH / 2 - 200); // Adjusted for better centering
        instructions.setTranslateY((double) SCREEN_HEIGHT / 2 - 150); // Adjusted for better centering

        // Create a back button with the same size as the instructions
        Button backButton = new Button("Back to Main Menu");
        backButton.setMinSize(400, 100); // Set the button size to match the instructions
        backButton.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-background-color: #3A9; -fx-text-fill: #FFF;");

        backButton.setTranslateX((double) SCREEN_WIDTH / 2 - 200); // Center the button horizontally
        backButton.setTranslateY((double) SCREEN_HEIGHT / 2 - 150); // Positioned below the instructions

        backButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show(stage);
        });

        contentLayout.getChildren().addAll(instructions, backButton);
        tutorialLayout.getChildren().add(contentLayout);

        Scene tutorialScene = new Scene(tutorialLayout, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(tutorialScene);
        stage.show();
    }
}