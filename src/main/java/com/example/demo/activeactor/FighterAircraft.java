package com.example.demo.activeactor;

import com.example.demo.SoundManager;

/**
 * Abstract base class for all {@link FighterAircraft} in the game.
 * Provides common functionality for health management, {@link Missile} firing,
 * and position calculations for both {@link PlayerAircraft} and {@link EnemyAircraft}.
 * Extends {@link ActiveActorDestructible} to inherit basic actor properties.
 */
public abstract class FighterAircraft extends ActiveActorDestructible {

	/** Current health points */
	private int health;

	/**
	 * Constructs a new {@link FighterAircraft} with specified initial properties.
	 *
	 * @param imageName The filename of the image
	 * @param imageHeight The height of the image
	 * @param initialXPos The initial X-coordinate position
	 * @param initialYPos The initial Y-coordinate position
	 * @param health The initial health points
	 */
	public FighterAircraft(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Creates and returns a {@link Missile} fired by this {@link FighterAircraft}.
	 * Implementation varies based on the specific type of {@link FighterAircraft}.
	 *
	 * @return A new {@link Missile} instance of type  {@link ActiveActorDestructible}
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Handles damage taken by the {@link FighterAircraft}. Decrements health and
	 * destroys the {@link FighterAircraft} if health reaches zero, playing a destruction sound effect.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
			SoundManager.playSound("P1942_00003.wav");
		}
	}

	/**
	 * Calculates the X-coordinate position for spawning  {@link Missile}.
	 *
	 * @param xPositionOffset The horizontal offset from the {@link FighterAircraft}'s position
	 * @return The calculated X-coordinate for the  {@link Missile} spawn point
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y-coordinate position for spawning  {@link Missile}.
	 *
	 * @param yPositionOffset The vertical offset from the {@link FighterAircraft} position
	 * @return The calculated Y-coordinate for the {@link Missile} spawn point
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the {@link FighterAircraft}'s health has reached zero.
	 *
	 * @return true if health is zero, false otherwise
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health points of the {@link FighterAircraft}.
	 *
	 * @return The current health value
	 */
	public int getHealth() {
		return health;
	}

}