package com.example.demo.userinterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * The {@code HeartDisplay} class is responsible for displaying a specified number
 * of heart icons within an {@code HBox} layout. The hearts represent a visual
 * indicator, such as a health bar in a game.
 */
public class HeartDisplay {

	/** Path to the heart image resource. */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/** Height of the heart image. */
	private static final int HEART_HEIGHT = 50;

	/** Index of the first item in the container. */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/** Container to hold the heart images. */
	private HBox container;

	/** X-coordinate position of the container. */
	private final double containerXPosition;

	/** Y-coordinate position of the container. */
	private final double containerYPosition;

	/** Number of hearts to display. */
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@code HeartDisplay} object.
	 *
	 * @param xPosition the X-coordinate position of the heart display container.
	 * @param yPosition the Y-coordinate position of the heart display container.
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
	 * Initializes the {@code HBox} container for holding the heart images.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Populates the container with the specified number of heart images.
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
	 * Removes one heart from the container, starting from the first heart.
	 * If the container is empty, no action is performed.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Returns the {@code HBox} container holding the heart images.
	 *
	 * @return the {@code HBox} container.
	 */
	public HBox getContainer() {
		return container;
	}
}
