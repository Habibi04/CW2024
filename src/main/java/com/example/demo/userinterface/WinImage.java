package com.example.demo.userinterface;

import com.example.demo.activeactor.PlayerAircraft;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@link WinImage} class represents a graphical "You Win" image displayed
 * when the {@link PlayerAircraft} achieves victory in the game. It extends {@link ImageView}
 * to handle the image display and positioning.
 */
public class WinImage extends ImageView {

	/** The relative path to the {@link WinImage} resource. */
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	/** The height of the {@link WinImage}. */
	private static final int HEIGHT = 500;

	/** The width of the {@link WinImage}. */
	private static final int WIDTH = 600;

	/**
	 * Constructs a new {@link WinImage} at the specified position on the screen.
	 *
	 * @param xPosition the x-coordinate .
	 * @param yPosition the y-coordinate .
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm())); // Load the "You Win" image.
		this.setVisible(false); // Initially set the image to be invisible.
		this.setFitHeight(HEIGHT); // Set the height of the image.
		this.setFitWidth(WIDTH); // Set the width of the image.
		this.setLayoutX(xPosition); // Set the x-coordinate of the image.
		this.setLayoutY(yPosition); // Set the y-coordinate of the image.
	}

	/**
	 * Makes the {@link WinImage} visible on the screen.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}
}
