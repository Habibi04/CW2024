package com.example.demo.activeactor;

/**
 * Represents a {@link Missile} fired by {@link EnemyAircraft} in the game.
 * This class extends the {@link Missile} class with specific characteristics
 * such as a unique enemy fire image and a left-moving trajectory.
 */
public class EnemyMissile extends Missile {

	/** Filename of the image */
	private static final String IMAGE_NAME = "enemyFire.png";

	/** Standard height of the image */
	private static final int IMAGE_HEIGHT = 15;

	/** Horizontal velocity of the {@link EnemyMissile} moving left */
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs an {@link EnemyMissile} with specified initial position.
	 *
	 * @param initialXPos The initial x-coordinate position
	 * @param initialYPos The initial y-coordinate position
	 */
	public EnemyMissile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position by moving horizontally.
	 *
	 * Moves to the left at a constant velocity.
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