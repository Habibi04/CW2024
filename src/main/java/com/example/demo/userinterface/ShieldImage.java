package com.example.demo.userinterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@link ShieldImage} class represents a graphical shield element in the user interface.
 * It extends {@link ImageView} to manage the display and visibility of the {@link ShieldImage}.
 */
public class ShieldImage extends ImageView {

	/** The relative path to the {@link ShieldImage} resource. */
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";

	/** The size of the {@link ShieldImage} (both width and height). */
	private static final int SHIELD_SIZE = 200;

	/**
	 * Constructs a new {@link ShieldImage} at the specified position.
	 *
	 * @param xPosition the x-coordinate.
	 * @param yPosition the y-coordinate.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition); // Set the x-coordinate of the shield.
		this.setLayoutY(yPosition); // Set the y-coordinate of the shield.
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm())); // Load the shield image.
		this.setVisible(false); // Initially set the shield to be invisible.
		this.setFitHeight(SHIELD_SIZE); // Set the height of the shield.
		this.setFitWidth(SHIELD_SIZE); // Set the width of the shield.
	}

	/**
	 * Makes the {@link ShieldImage} visible on the screen.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the {@link ShieldImage} from the screen.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}
