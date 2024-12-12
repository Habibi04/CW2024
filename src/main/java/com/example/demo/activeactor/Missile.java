package com.example.demo.activeactor;

/**
 * An abstract base class representing {@link Missile}s in the game.
 *
 * Extends {@link ActiveActorDestructible} to provide a common implementation
 * for different types of {@link Missile}s, with a standard damage and destruction mechanism.
 */
public abstract class Missile extends ActiveActorDestructible {

	/**
	 * Constructs a {@link Missile} with specified image and initial position.
	 *
	 * @param imageName The filename of the image
	 * @param imageHeight The desired height of the image
	 * @param initialXPos The initial x-coordinate position
	 * @param initialYPos The initial y-coordinate position
	 */
	public Missile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles damage taken
	 * When a {@link Missile} takes damage, it is immediately destroyed.
	 * This method provides a default implementation for {@link Missile} destruction.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Abstract method to update the {@link Missile}'s position.
	 * Must be implemented by subclasses to define specific movement behavior
	 * for different types of {@link Missile}s.
	 */
	@Override
	public abstract void updatePosition();
}