package com.example.demo.activeactor;

import com.example.demo.level.LevelParent;

/**
 * Represents an {@link EnemyAircraft} in the game.
 *
 * This class extends {@link FighterAircraft} and defines specific behavior
 * for {@link EnemyAircraft}, including movement, firing mechanics, and interaction
 * with the game level.
 */
public class EnemyAircraft extends FighterAircraft {

	/** Filename of the {@link EnemyAircraft} image */
	private static final String IMAGE_NAME = "enemyplane.png";

	/** Standard height of the {@link EnemyAircraft} image */
	private static final int IMAGE_HEIGHT = 50;

	/** Horizontal velocity of the {@link EnemyAircraft} moving left */
	private static final int HORIZONTAL_VELOCITY = -6;

	/** X-axis offset for {@link Missile} spawn position */
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

	/** Y-axis offset for {@link Missile} spawn position */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;

	/** Initial health points for the {@link EnemyAircraft} */
	private static final int INITIAL_HEALTH = 1;

	/** Probability of firing a {@link Missile} in each update cycle */
	private static final double FIRE_RATE = .01;

	/** Reference to the current game level */
	private final LevelParent levelParent;

	/**
	 * Constructs an {@link EnemyAircraft} with specified position and level context.
	 *
	 * @param levelParent The parent level managing this {@link EnemyAircraft}
	 * @param initialXPos The initial x-coordinate position of the aircraft
	 * @param initialYPos The initial y-coordinate position of the aircraft
	 */
	public EnemyAircraft(LevelParent levelParent, double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);

		this.levelParent = levelParent;
	}

	/**
	 * Updates the aircraft's position by moving horizontally.
	 *
	 * Moves the {@link EnemyAircraft} to the left at a constant velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Attempts to fire a {@link Missile} based on a random fire rate.
	 *
	 * @return An {@link EnemyMissile} if fired, null otherwise
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyMissile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	/**
	 * Updates by moving and potentially spawning a {@link Missile}.
	 *
	 * Calls updatePosition() and attempts to spawn a {@link Missile}
	 * through the {@link LevelParent}.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		levelParent.spawnEnemyProjectile(fireProjectile());
	}
}