package com.example.demo.level;

import com.example.demo.activeactor.Boss;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(this);
	}

	/**
	 * Initializes friendly units for the current game level.
	 * Adds the user's game character to the root scene or container.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks the current game state to determine if the game is over.
	 * <p>
	 * Implements two primary game-ending conditions:
	 * 1. If the user's character is destroyed, the game is lost
	 * 2. If the boss is destroyed, the game progresses to the next level
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			// Instead of winGame(), transition to next level
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns {@link com.example.demo.activeactor.EnemyAircraft} for the current game level.
	 * <p>
	 * This method adds the {@link Boss}.
	 * Also adds the {@link Boss} shield image to the game root.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
			getRoot().getChildren().add(boss.getShieldImage());
		}
	}

	/**
	 * Creates and instantiates the view for the current game level.
	 *
	 * @return The instantiated {@link LevelView} for Level Two
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}
}