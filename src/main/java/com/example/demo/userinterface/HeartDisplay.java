package com.example.demo.userinterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * The {@link HeartDisplay} class is responsible for displaying a specified number
 * of heart icons within an {@link HBox} layout. The hearts represent a visual
 * indicator, such as a health bar in a game.
 */
public class HeartDisplay {

	/** Path to the {@link HeartDisplay}. */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/** Height of the {@link HeartDisplay}. */
	private static final int HEART_HEIGHT = 50;

	/** Index of the first item in the {@link HBox}. */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/** {@link HBox} to hold the {@link HeartDisplay}. */
	private HBox container;

	/** X-coordinate position of the {@link HBox}. */
	private final double containerXPosition;

	/** Y-coordinate position of the {@link HBox}. */
	private final double containerYPosition;

	/** Number of hearts to display. */
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@link HeartDisplay} object.
	 *
	 * @param xPosition the X-coordinate position.
	 * @param yPosition the Y-coordinate position.
	 * @param heartsToDisplay the number of hearts to initially display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the {@link HBox} container for holding the {@link HeartDisplay}.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Populates the {@link HBox} with the specified number of {@link HeartDisplay}.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the {@link HBox}, starting from the first heart.
	 * If the {@link HBox} is empty, no action is performed.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Returns the {@link HBox} container holding the heart images.
	 *
	 * @return the {@link HBox} container.
	 */
	public HBox getContainer() {
		return container;
	}
}
