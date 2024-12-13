package com.example.demo.level;

import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.activeactor.EnemyAircraft;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Determines if the game should end and handles the appropriate action based on the {@link com.example.demo.activeactor.PlayerAircraft}
	 * current state.
	 * When the user's character is destroyed, the game is ended with a loss by invoking the
	 * {@link #loseGame()} method. If the required number of kills has been achieved, the
	 * player transitions to the next level using {@link #goToNextLevel(String)}.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Initializes the {@link com.example.demo.activeactor.PlayerAircraft} into the scene .friendly units specific to the current level by adding the
	 * {@link com.example.demo.activeactor.PlayerAircraft} to the root node of the game.
	 * The {@link com.example.demo.activeactor.PlayerAircraft} is retrieved using {@link #getUser()} and then added
	 * to the game scene graph through the {@link #getRoot()}'s children nodes.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns {@link EnemyAircraft} for the current level based on the total number of enemies allowed
	 * and a predefined probability. Newly created enemies are added to the game via
	 * {@link #addEnemyUnit(ActiveActorDestructible)}.
	 * The enemy units are instances of {@link EnemyAircraft}, created with the level's screen width
	 * as their initial X position, and a randomized Y position within bounds as calculated using
	 * {@link #getEnemyMaximumYPosition()}.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyAircraft(this, getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Creates and returns a {@link LevelView} instance for this level, initialized with the
	 * root node and {@link com.example.demo.activeactor.PlayerAircraft} initial health. Manages elements like hearts, {@link com.example.demo.userinterface.WinImage}, and
	 * {@link com.example.demo.userinterface.GameOverImage} screens.
	 *
	 * @return A configured {@link LevelView} instance.
	 */

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks whether the {@link com.example.demo.activeactor.PlayerAircraft} has achieved the required number of kills
	 * to progress to the next level.
	 *
	 * @return true if the {@link com.example.demo.activeactor.PlayerAircraft}  has reached or exceeded the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
