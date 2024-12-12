package com.example.demo.controller;

import com.example.demo.userinterface.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The {@link Main} class serves as the entry point for the application. It extends
 * {@link Application} to launch the JavaFX application and set up the {@link MainMenu}.
 */
public class Main extends Application {

	/** The width of the application window. */
	private static final int SCREEN_WIDTH = 1300;

	/** The height of the application window. */
	private static final int SCREEN_HEIGHT = 750;

	/** The title of the application window. */
	private static final String TITLE = "Sky Battle";

	/** The {@link Controller} instance used to manage the game logic. */
	private Controller myController;

	/**
	 * The main entry point for the JavaFX application. This method sets up the
	 * primary stage, including its size, title, and the initial scene.
	 *
	 * @param stage the primary stage for this application.
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);

		MainMenu mainMenu = new MainMenu();
		mainMenu.show(stage);
	}

	/**
	 * The {@link Main} method, which serves as the entry point for the application.
	 * It calls {@link Application#launch(String...)} to start the JavaFX application.
	 * @param args the command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		launch(); // Launch the JavaFX application.
	}
}
