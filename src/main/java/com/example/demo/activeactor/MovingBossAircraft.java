package com.example.demo.activeactor;

import com.example.demo.level.LevelParent;

/**
 * Represents a {@link MovingBossAircraft} enemy that travels horizontally across the screen.
 * This {@link MovingBossAircraft} type has the ability to fire {@link EnemyMissile} and maintains a constant horizontal movement.
 * Extends {@link FighterAircraft} to inherit basic aircraft functionality.
 */
public class MovingBossAircraft extends FighterAircraft {

	/** The filename of the image */
	private static final String IMAGE_NAME = "bossplane.png";

	/** Height of the image in pixels */
	private static final int IMAGE_HEIGHT = 50;

	/** Speed at which the {@link Boss} moves horizontally (negative for leftward movement) */
	private static final int HORIZONTAL_VELOCITY = -6;

	/** Horizontal offset for projectile spawn position */
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

	/** Vertical offset for projectile spawn position */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;

	/** Starting health points */
	private static final int INITIAL_HEALTH = 10;

	/** Probability of firing a {@link EnemyMissile} per frame */
	private static final double FIRE_RATE = .01;

	/** Reference to the {@link LevelParent} controlling this {@link Boss} instance */
	private final LevelParent levelParent;

	/**
	 * Constructs a new {@link MovingBossAircraft} at the specified position.
	 *
	 * @param levelParent The parent level managing this {@link Boss} instance
	 * @param initialXPos The initial X-coordinate position of the {@link Boss}
	 * @param initialYPos The initial Y-coordinate position of the {@link Boss}
	 */
	public MovingBossAircraft(LevelParent levelParent, double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		this.levelParent = levelParent;
	}

	/**
	 * Updates the {@link Boss}'s position by moving it horizontally across the screen
	 * at a constant velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Attempts to create a new {@link EnemyMissile} based on the fire rate probability.
	 * If a {@link EnemyMissile} is created, its position is offset from the boss's current position.
	 * @return A new {@link EnemyMissile} if firing conditions are met, null otherwise
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
	 * Performs per-frame updates for the {@link Boss} including position updates
	 * and {@link EnemyMissile} spawning attempts.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		levelParent.spawnEnemyProjectile(fireProjectile());
	}

}