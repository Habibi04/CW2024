package com.example.demo.activeactor;

import com.example.demo.SoundManager;

/**
 * Represents the {@link PlayerAircraft} in the game.
 * Handles {@link PlayerAircraft} movement, boundary checking, projectile firing, and kill counting.
 * Extends {@link FighterAircraft} to inherit basic aircraft functionality.
 */
public class PlayerAircraft extends FighterAircraft {

	/** The filename of image */
	private static final String IMAGE_NAME = "userplane.png";
	/** Right boundary limit for movement */
	private static final double X_LOWER_BOUND = 1000;
	/** Left boundary limit for movement */
	private static final double X_UPPER_BOUND = 0;
	/** Upper boundary limit for movement */
	private static final double Y_UPPER_BOUND = -40;
	/** Lower boundary limit for movement */
	private static final double Y_LOWER_BOUND = 600.0;
	/** Starting X position for */
	private static final double INITIAL_X_POSITION = 5.0;
	/** Starting Y position for */
	private static final double INITIAL_Y_POSITION = 300.0;
	/** Height in pixels */
	private static final int IMAGE_HEIGHT = 50;
	/** Base horizontal movement speed */
	private static final int HORIZONTAL_VELOCITY = 8;
	/** Base vertical movement speed */
	private static final int VERTICAL_VELOCITY = 8;
	/** Horizontal offset for {@link UserMissile} spawn position */
	private static final int PROJECTILE_X_POSITION = 110;
	/** Vertical offset for {@link UserMissile} spawn position */
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

	/** Direction multiplier for horizontal movement (-1 for left, 0 for none, 1 for right) */
	private int velocityMultiplierHorizontal;
	/** Direction multiplier for vertical movement (-1 for up, 0 for none, 1 for down) */
	private int velocityMultiplierVertical;
	/** Counter for {@link EnemyAircraft} destroyed by the {@link PlayerAircraft} */
	private int numberOfKills;

	/**
	 * Constructs a new {@link PlayerAircraft} with specified initial health.
	 *
	 * @param initialHealth The starting health points of the {@link PlayerAircraft}
	 */
	public PlayerAircraft(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplierVertical = 0;
	}

	/**
	 * Updates the position based on current movement multipliers.
	 * Checks and enforces boundary limits for both horizontal and vertical movement.
	 */
	@Override
	public void updatePosition() {
		if (isMovingHorizontal()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * velocityMultiplierHorizontal);
			double newPosition = getLayoutX() + getTranslateX();
			if (newPosition < X_UPPER_BOUND || newPosition > X_LOWER_BOUND)
				this.setTranslateX(initialTranslateX);
		}

		if (isMovingVertical()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplierVertical);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Performs per-frame updates for the {@link PlayerAircraft}.
	 * Currently only updates position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Creates and returns a new {@link PlayerAircraft} {@link UserMissile}.
	 * Plays a sound effect when firing.
	 *
	 * @return A new {@link UserMissile} instance positioned relative to the {@link PlayerAircraft}.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		SoundManager.playSound("P1942_00004.wav");
		return new UserMissile(getTranslateX() + 128, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if it is currently moving horizontally.
	 *
	 * @return true if moving left or right, false if stationary
	 */
	private boolean isMovingHorizontal() {
		return velocityMultiplierHorizontal != 0;
	}

	/**
	 * Checks if it is currently moving vertically.
	 *
	 * @return true if moving up or down, false if stationary
	 */
	private boolean isMovingVertical() {
		return velocityMultiplierVertical != 0;
	}

	/**
	 * Initiates upward movement.
	 */
	public void moveUp() {
		velocityMultiplierVertical = -1;
	}

	/**
	 * Initiates downward movement.
	 */
	public void moveDown() {
		velocityMultiplierVertical = 1;
	}

	/**
	 * Initiates leftward movement.
	 */
	public void moveLeft() {
		velocityMultiplierHorizontal = -1;
	}

	/**
	 * Initiates rightward movement.
	 */
	public void moveRight() {
		velocityMultiplierHorizontal = 1;
	}

	/**
	 * Stops horizontal movement.
	 */
	public void stopHorizontal() {
		velocityMultiplierHorizontal = 0;
	}

	/**
	 * Stops vertical movement.
	 */
	public void stopVertical() {
		velocityMultiplierVertical = 0;
	}

	/**
	 * Gets the current number of {@link EnemyAircraft} destroyed by the {@link PlayerAircraft}.
	 *
	 * @return The number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill counter when an {@link EnemyAircraft} is destroyed.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Decrements the kill counter.
	 * Used when correcting kill count or implementing specific game mechanics.
	 */
	public void decrementKillCount() {
		numberOfKills--;
	}
}