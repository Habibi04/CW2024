package com.example.demo.userinterface;

import com.example.demo.userinterface.MainMenu;
import com.example.demo.controller.Controller;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MainMenuTest extends ApplicationTest {

    @Test
    public void testShow() {
        // Call the method under test
        MainMenu mainMenu = new MainMenu();
        mainMenu.show(getTestStage());

        // Verify that the scene is set on the stage
        Scene scene = getTestStage().getScene();
        assertNotNull(scene);

        // Verify scene dimensions
        assertEquals(MainMenu.getScreenWidth(), scene.getWidth());
        assertEquals(MainMenu.getScreenHeight(), scene.getHeight());

        // Verify the root layout is a Group
        Group root = (Group) scene.getRoot();
        assertNotNull(root);

        // Verify background image is set
        ImageView background = (ImageView) root.getChildren().get(0);
        assertNotNull(background);
        assertNotNull(background.getImage());

        // Verify button layout is present
        Group menuLayout = (Group) root.getChildren().get(1);
        assertNotNull(menuLayout);

        // Verify buttons are present with correct text and styles
        Group buttonLayout = (Group) menuLayout.getChildren().get(0);
        Button startGameButton = (Button) buttonLayout.getChildren().get(0);
        Button tutorialButton = (Button) buttonLayout.getChildren().get(1);
        Button exitButton = (Button) buttonLayout.getChildren().get(2);

        assertEquals("Start Game", startGameButton.getText());
        assertEquals("Tutorial", tutorialButton.getText());
        assertEquals("Exit", exitButton.getText());

        // Simulate exit button click and verify stage is closed
        exitButton.fire();
        Platform.runLater(() -> {
            assertFalse(getTestStage().isShowing());
        });
    }
}