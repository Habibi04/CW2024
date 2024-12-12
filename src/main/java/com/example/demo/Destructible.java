package com.example.demo;

/**
 * The {@link Destructible} interface defines the behavior for objects that can take damage
 * and be destroyed within the application.
 */
public interface Destructible {

	/**
	 * Handles the logic for an object taking damage.
	 * Called when object takes damage but not destroyed.
	 */
	void takeDamage();

	/**
	 * Handles the logic for destroying the object.
	 * Called when object completely destroyed
	 */
	void destroy();
}
