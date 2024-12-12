package com.example.demo.activeactor;

import com.example.demo.Destructible;
import com.example.demo.SoundManager;
import com.example.demo.userinterface.HeartDisplay;

/**
 * An abstract base class for {@link ActiveActorDestructible} in the game.
 * Extends {@link ActiveActor} and implements the {@link Destructible} interface,
 * providing a foundation for game objects that can move, update,
 * and be destroyed.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/** Tracks whether the destruction occurs */
	private boolean isDestroyed;

	/**
	 * Constructs a destructible {@link ActiveActor} with initial image and position.
	 *
	 * @param imageName The filename of the image
	 * @param imageHeight The desired height of the image
	 * @param initialXPos The initial x-coordinate position
	 * @param initialYPos The initial y-coordinate position
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Abstract method to update position.
	 *
	 * Must be implemented by subclasses to define specific movement behavior.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method to perform general updates.
	 *
	 * Subclasses must implement specific update logic.
	 */
	public abstract void updateActor();

	/**
	 * Abstract method to handle damage taken.
	 *
	 * Subclasses must define the response to receiving damage.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the destruction.
	 *
	 * Sets the destroyed state to true, indicating there's no activity.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state.
	 *
	 * @param isDestroyed Boolean indicating whether there is destruction
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor has been destroyed.
	 *
	 * @return True if destroyed, false otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}