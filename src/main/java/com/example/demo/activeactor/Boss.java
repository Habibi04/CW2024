package com.example.demo.activeactor;

import com.example.demo.level.LevelParent;
import com.example.demo.userinterface.ShieldImage;

import java.util.*;

/**
 * Represents a {@link Boss} enemy aircraft in the game that implements advanced combat mechanics.
 * The {@link Boss} features a dynamic shield system, pattern-based movement, and projectile attacks.
 */
public class Boss extends FighterAircraft {

	/** The filename of image resource */
	private static final String IMAGE_NAME = "bossplane.png";
	/** Initial X-coordinate position for spawning */
	private static final double INITIAL_X_POSITION = 800;
	/** Initial Y-coordinate position for spawning */
	private static final double INITIAL_Y_POSITION = 400;
	/** Vertical offset for {@link BossMissile} spawn position relative to {@link Boss} position */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	/** Probability of {@link Boss} firing a {@link BossMissile} per frame */
	private static final double BOSS_FIRE_RATE = .04;
	/** Probability of {@link Boss} activating its shield per frame */
	private static final double BOSS_SHIELD_PROBABILITY = 0.0005;
	/** Height of the {@link Boss} sprite in pixels */
	private static final int IMAGE_HEIGHT = 100;
	/** Speed of vertical movement */
	private static final int VERTICAL_VELOCITY = 8;
	/** Initial health points of the {@link Boss} */
	private static final int HEALTH = 20;
	/** Number of movement patterns in a single cycle */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	/** Constant representing no movement */
	private static final int ZERO = 0;
	/** Maximum number of frames the {@link Boss} can move in the same direction */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	/** Upper boundary for {@link Boss}'s vertical movement */
	private static final int Y_POSITION_UPPER_BOUND = -100;
	/** Lower boundary for {@link Boss}'s vertical movement */
	private static final int Y_POSITION_LOWER_BOUND = 475;
	/** Maximum duration the shield can remain active */
	private static final int MAX_FRAMES_WITH_SHIELD = 500;

	/** Reference to the parent level controlling this {@link Boss} instance */
	private final LevelParent levelParent;
	/** List storing the {@link Boss}'s movement pattern sequence */
	private final List<Integer> movePattern;
	/** Current shield status */
	private boolean isShielded;
	/** Counter for consecutive moves in the same direction */
	private int consecutiveMovesInSameDirection;
	/** Current index in the movement pattern */
	private int indexOfCurrentMove;
	/** Duration counter for active shield */
	private int framesWithShieldActivated;
	/** Visual representation of the {@link Boss}'s shield */
	private final ShieldImage shieldImage;

	/**
	 * Constructs a new {@link Boss} instance with initialized position and attributes.
	 * Sets up the movement pattern, shield system, and connects to the {@link LevelParent}.
	 *
	 * @param levelParent The parent level managing this {@link Boss} instance
	 */
	public Boss(LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.levelParent = levelParent;
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldImage = new ShieldImage(INITIAL_X_POSITION-75, INITIAL_Y_POSITION );
		initializeMovePattern();
	}

	/**
	 * Updates the {@link Boss}'s vertical position based on its movement pattern.
	 * Ensures the {@link Boss} stays within the game boundaries and updates shield position.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		shieldImage.setLayoutY(currentPosition);
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Performs per-frame updates for the {@link Boss} including position, shield status,
	 * and {@link BossMissile} spawning.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		levelParent.spawnEnemyProjectile(fireProjectile());
	}

	/**
	 * Attempts to create a new {@link BossMissile} based on the {@link Boss}'s fire rate.
	 *
	 * @return A new {@link BossMissile} if firing conditions are met, null otherwise
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossMissile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles damage application to the {@link Boss}.
	 * Damage is only applied if the shield is not active.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the {@link Boss}'s movement pattern with alternating vertical movements.
	 * Creates a sequence of upward, downward, and stationary movements that are randomly shuffled.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the {@link Boss}'s shield status including activation, duration tracking,
	 * and deactivation when duration is exceeded.
	 */
	private void updateShield() {
		if (isShielded){
			framesWithShieldActivated++;
			shieldImage.showShield();
		}
		else if (shieldShouldBeActivated()) activateShield();
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Determines the next movement value from the pattern.
	 * Reshuffles the pattern when maximum consecutive moves are reached.
	 *
	 * @return The next movement value to be applied
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the {@link Boss} should fire a {@link BossMissile} in the current frame.
	 *
	 * @return true if firing conditions are met, false otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial Y-position for spawned {@link BossMissile}.
	 *
	 * @return The Y-coordinate where new {@link BossMissile} should spawn
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Checks if the shield should be activated based on random probability.
	 *
	 * @return true if shield should activate, false otherwise
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks if the shield has been active for its maximum duration.
	 *
	 * @return true if shield duration has been exhausted, false otherwise
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the {@link Boss}'s shield and updates the shield visual effect.
	 */
	private void activateShield() {
		isShielded = true;
		shieldImage.showShield();
	}

	/**
	 * Deactivates the {@link Boss}'s shield and resets the shield duration counter.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.showShield();
	}

	/**
	 * Gets the shield image object associated with this {@link Boss}.
	 *
	 * @return The {@link ShieldImage} instance used for visual shield effects
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}
}