package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;
import com.example.demo.MainMenu;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;
	private MainMenu mainMenu;

	public Controller(Stage stage) {
		this.stage = stage;

		// Initialize the main menu
		mainMenu = new MainMenu(stage);
		mainMenu.addObserver(this);
	}

	public void launchGame() {
		// Set a default size for the stage (it will remain fixed in this example)
		stage.setWidth(800);
		stage.setHeight(600);

		// Start with the main menu scene
		Scene mainMenuScene = mainMenu.createMainMenuScene();
		stage.setScene(mainMenuScene);
		stage.show();  // Display the stage

		// Ensure the Main Menu scene is displayed first
		mainMenu.createMainMenuScene();
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		// Use reflection to instantiate the level class dynamically
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		// Add observer to the level
		myLevel.addObserver(this);

		// Initialize the scene for the level and set it on the stage
		Scene levelScene = myLevel.initializeScene();
		stage.setScene(levelScene);

		// Start the level game
		myLevel.startGame();
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
