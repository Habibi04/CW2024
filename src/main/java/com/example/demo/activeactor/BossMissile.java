package com.example.demo.activeactor;

/**
 * Represents a {@link Missile} fired by the {@link Boss} in the game.
 * This class extends the {@link Missile} class with specific characteristics
 * such as a fireball image and left-moving trajectory.
 */
public class BossMissile extends Missile {

	/** Filename of the {@link BossMissile} image */
	private static final String IMAGE_NAME = "fireball.png";

	/** Standard height of the {@link BossMissile} image */
	private static final int IMAGE_HEIGHT = 25;

	/** Horizontal velocity of the {@link Missile} moving left */
	private static final int HORIZONTAL_VELOCITY = -15;

	/** Initial x-coordinate position for the {@link BossMissile} */
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a {@link BossMissile} with a specified vertical position.
	 *
	 * @param initialYPos The initial y-coordinate position of the {@link Missile}
	 */
	public BossMissile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the {@link BossMissile}'s position by moving horizontally.
	 *
	 * Moves the {@link BossMissile} to the left at a constant velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor by calling the position update method.
	 *
	 * In this implementation, updating the actor simply means updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}