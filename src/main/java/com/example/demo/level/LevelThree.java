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
     *
     * This method checks two potential game-ending conditions:
     * 1. If the user's character has been destroyed, the game will end, triggering the `loseGame` method.
     * 2. If the user has achieved the required kill target, the game will transition to the next level using `goToNextLevel`.
     *
     * The kill target and the next level are defined by level-specific constants.
     * This method should be invoked periodically to monitor the game's progression state.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget())
            goToNextLevel(NEXT_LEVEL);
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
