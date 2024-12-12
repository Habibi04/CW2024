package com.example.demo.activeactor;

import javafx.scene.image.*;

/**
 * An abstract base class representing an {@link ActiveActor} in the game.
 *
 * Extends ImageView to provide a base for game objects that can move and update
 * their position. Provides common functionality for image loading, positioning,
 * and basic movement methods.
 */
public abstract class ActiveActor extends ImageView {

	/** Base directory path for loading images */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an {@link ActiveActor} with a specified image and initial position.
	 *
	 * @param imageName The filename of the image to be loaded
	 * @param imageHeight The desired height of the image
	 * @param initialXPos The initial x-coordinate position
	 * @param initialYPos The initial y-coordinate position
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		//this.setImage(new Image(IMAGE_LOCATION + imageName));
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Abstract method to update the position.
	 *
	 * Must be implemented by subclasses to define specific movement behavior.
	 */
	public abstract void updatePosition();

	/**
	 * Moves horizontally by a specified distance.
	 *
	 * @param horizontalMove The amount to move along the x-axis
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves vertically by a specified distance.
	 *
	 * @param verticalMove The amount to move along the y-axis
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}