package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.SoundManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import com.example.demo.level.LevelParent;
import com.example.demo.userinterface.MainMenu;
import com.example.demo.userinterface.PauseMenu;
import com.example.demo.userinterface.Tutorial;

/**
 * The {@link Controller} class manages the game flow, including transitioning between levels,
 * handling the {@link PauseMenu}, and managing user inputs. It implements {@link Observer} to respond
 * to events from levels.
 */
public class Controller implements Observer {

	/** The fully qualified class name for {@link com.example.demo.level.LevelOne}. */
	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";

	/** The primary {@link Stage} for displaying game scenes. */
	private final Stage stage;

	/** The {@link PauseMenu} for the game. */
	private PauseMenu pauseMenu;

	/** The {@link Scene} representing the current level. */
	private Scene levelScene;

	/** The currently active level, extending {@link LevelParent}. */
	private LevelParent currentLevel;

	/**
	 * Constructs a {@link Controller} for managing game flow.
	 *
	 * @param stage the primary {@link Stage} for displaying scenes.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game by starting {@link com.example.demo.level.LevelOne}.
	 *
	 * @throws ClassNotFoundException if the {@link LevelParent} class cannot be found.
	 * @throws InvocationTargetException if an error occurs during {@link LevelParent} instantiation.
	 * @throws NoSuchMethodException if the {@link LevelParent} class constructor is missing.
	 * @throws InstantiationException if the {@link LevelParent} class cannot be instantiated.
	 * @throws IllegalAccessException if the {@link LevelParent} class constructor is inaccessible.
	 */
	public void launchGame() throws ClassNotFoundException, InvocationTargetException,
			NoSuchMethodException, InstantiationException, IllegalAccessException {
		stage.show();
		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Transitions to a specified level using reflection to dynamically load the {@link LevelParent} class.
	 *
	 * @param className the fully qualified class name of the {@link LevelParent} to load.
	 * @throws ClassNotFoundException if the {@link LevelParent} class cannot be found.
	 * @throws NoSuchMethodException if the {@link LevelParent} class constructor is missing.
	 * @throws InstantiationException if the {@link LevelParent} class cannot be instantiated.
	 * @throws IllegalAccessException if the {@link LevelParent} class constructor is inaccessible.
	 * @throws InvocationTargetException if an error occurs during {@link LevelParent} instantiation.
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		SoundManager.playBackgroundSound();

		Class<?> levelClass = Class.forName(className);
		Constructor<?> constructor = levelClass.getConstructor(double.class, double.class);
		currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		currentLevel.setStage(stage);

		currentLevel.addObserver(this);

		levelScene = currentLevel.initializeScene();

		pauseMenu = new PauseMenu(
				levelScene,
				currentLevel::resumeGame,
				() -> {
					MainMenu mainMenu = new MainMenu();
					mainMenu.show(stage);
				},
				this::restartCurrentLevel
		);

		levelScene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				currentLevel.pauseGame();
				pauseMenu.show();
			}
		});

		stage.setScene(levelScene);
		currentLevel.startGame();
	}

	/**
	 * Restarts the current level by reloading it.
	 */
	private void restartCurrentLevel() {
		try {
			currentLevel.pauseGame();
			goToLevel(currentLevel.getClass().getName());
		} catch (Exception e) {
			throw new RuntimeException("Failed to restart the level.", e);
		}
	}

	/**
	 * Handles updates from observable objects (e.g., levels) to transition to the next level.
	 *
	 * @param observable the observable object triggering the update.
	 * @param arg the argument passed by the observable, typically the next level's class name.
	 */
	@Override
	public void update(Observable observable, Object arg) {
		try {
			goToLevel((String) arg);
		} catch (Exception e) {
			// Show an error alert if transitioning to the next level fails
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error loading level: " + e.getMessage());
			alert.show();
		}
	}

	/**
	 * Opens the tutorial screen.
	 */
	public void openTutorial() {
		Tutorial tutorial = new Tutorial(stage);
		tutorial.show();
	}
}