package com.example.demo.userinterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@link GameOverImage} class represents an image displayed when the game is over.
 * It extends {@link ImageView} to set up and display the "{@link GameOverImage}" on the screen.
 */
public class GameOverImage extends ImageView {

	/** The relative path to the "{@link GameOverImage}" image resource. */
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.jpg";

	/**
	 * Constructs a new {@link GameOverImage} instance and initializes it with the specified
	 * position on the screen.
	 *
	 * @param xPosition the x-coordinate where the image will be displayed.
	 * @param yPosition the y-coordinate where the image will be displayed.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		setFitWidth(1300); // Set the width of the image.
		setFitHeight(750); // Set the height of the image.
		setLayoutX(xPosition); // Set the x-coordinate of the image.
		setLayoutY(yPosition); // Set the y-coordinate of the image.
	}
}
