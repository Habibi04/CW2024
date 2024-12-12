package com.example.demo.level;

import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.activeactor.EnemyAircraft;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelFour"; // Assuming you have a Level Four
    private static final int TOTAL_ENEMIES = 10;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = .30;
    private static final int PLAYER_INITIAL_HEALTH = 4;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Determines if the game has reached the end conditions for the current level.
     * This method checks two potential game-ending conditions:
     * 1. If the {@link com.example.demo.activeactor.PlayerAircraft}  character has been destroyed, the game will end, triggering the `loseGame` method.
     * 2. If the {@link com.example.demo.activeactor.PlayerAircraft}  has achieved the required kill target, the game will transition to the next level using `goToNextLevel`.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget())
            goToNextLevel(NEXT_LEVEL);
    }

    /**
     * Initializes the friendly units into the game scene for this level by adding the {@link com.example.demo.activeactor.PlayerAircraft}
     * to the root node of the game.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns {@link EnemyAircraft}  for the current level based on certain conditions and probabilities.
     * This method ensures that the number of enemy units does not exceed the total allowed for the level.
     * {@link EnemyAircraft}  are added with random Y positions within the permissible range. There is a probability.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();

                // Add possibility of spawning TankyEnemyPlane
                ActiveActorDestructible newEnemy;
                if (Math.random() < 0.3) { // 30% chance of spawning a tanky enemy
                    //newEnemy = new TankyEnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                    newEnemy = new EnemyAircraft(this, getScreenWidth(), newEnemyInitialYPosition);

                } else {
                    newEnemy = new EnemyAircraft(this, getScreenWidth(), newEnemyInitialYPosition);
                }

                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Instantiates the view for the current level.
     *
     * @return a new {@link LevelView} instance initialized with the root node and the player's initial health.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the {@link com.example.demo.activeactor.PlayerAircraft} has reached the required number of kills to advance to the next level.
     *
     * @return {@code true} if the  {@link com.example.demo.activeactor.PlayerAircraft} number of kills is greater than or equal to the required kill target,
     * {@code false} otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}

