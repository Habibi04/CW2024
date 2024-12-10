package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import com.example.demo.LevelParent;
import com.example.demo.MainMenu;
import com.example.demo.PauseMenu;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;
	private PauseMenu pauseMenu;
	private Scene levelScene;
	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		stage.show();
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		// Use reflection to instantiate the level class dynamically
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		currentLevel.setStage(stage);

		// Add observer to the level
		currentLevel.addObserver(this);

		// Initialize the scene for the level and set it on the stage
		levelScene = currentLevel.initializeScene();

		// Create pause menu
		pauseMenu = new PauseMenu(
				levelScene,
				() -> {
					// Resume game logic
					currentLevel.resumeGame();
				},
				() -> {
					// Return to main menu
					MainMenu mainMenu = new MainMenu();
					mainMenu.show(stage);
				}
		);

		// Add key handler to show pause menu
		levelScene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				currentLevel.pauseGame();
				pauseMenu.show();
			}
		});

		stage.setScene(levelScene);

		// Start the level game
		currentLevel.startGame();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			// Transition to the next level based on the class name
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// Show an error alert if there's an issue with loading the level
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error loading level: " + e.getMessage());
			alert.show();
		}
	}
}