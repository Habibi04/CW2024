package com.example.demo.activeactor;

/**
 * Represents a {@link UserMissile} fired by the {@link PlayerAircraft} in the game.
 * This {@link UserMissile} moves horizontally across the screen at a constant velocity.
 * Extends the base {@link UserMissile} class with specific behavior for user-fired projectiles.
 */
public class UserMissile extends Missile {

	/** The filename of the image */
	private static final String IMAGE_NAME = "userfire.png";

	/** Height of the image */
	private static final int IMAGE_HEIGHT = 15;

	/** Speed at which the {@link UserMissile} moves horizontally across the screen */
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a new {@link UserMissile} at the specified position.
	 *
	 * @param initialXPos The initial X-coordinate position
	 * @param initialYPos The initial Y-coordinate position
	 */
	public UserMissile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the {@link UserMissile}'s position by moving it horizontally across the screen
	 * at the defined velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Performs the per-frame update for the {@link UserMissile}.
	 * Currently only updates the position
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}