package com.example.demo.level;

import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.userinterface.MainMenu;
import com.example.demo.activeactor.MovingBossAircraft;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
    private static final int TOTAL_ENEMIES = 2;
    private static final int KILLS_TO_ADVANCE = 3;
    private static final double ENEMY_SPAWN_PROBABILITY = .30;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the game is over based on the {@link com.example.demo.activeactor.PlayerAircraft} state or their
     * achievement of the specified kill target.
     * This method determines the end state of the game by evaluating the
     * {@link com.example.demo.activeactor.PlayerAircraft}'s current status and accomplishments:
     * - If the {@link com.example.demo.activeactor.PlayerAircraft} has been destroyed, the {@link LevelParent#loseGame()} method is invoked
     *   to handle the defeat conditions.
     * - If the {@link com.example.demo.activeactor.PlayerAircraft} achieves the required number of kills to advance, the
     *   {@link LevelParent#winGame} method is called to handle the victory conditions.
     * This functionality is specific to {@link LevelFour} and overrides the parent
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget())
            winGame();
    }

    /**
     * Handles the win condition for the game by overriding the behavior from the {@link LevelParent}.
     * Creates a delayed transition of 3 seconds before navigating to the main menu screen.
     * Overrides:
     * - {@link LevelParent#winGame()} to add additional behavior specific to this level when the
     *   game win condition is met.
     * - The method relies on the {@link MainMenu} class to handle the transition to the main menu screen.
     */
    @Override
    protected void winGame() {
        super.winGame();

        pauseGame();

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(3),
                event -> {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.show(getStage());
                }
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * Initializes and adds the {@link com.example.demo.activeactor.PlayerAircraft} to the scene graph.
     * The implementation assumes that the {@link com.example.demo.activeactor.PlayerAircraft} has already been
     * instantiated.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns {@link com.example.demo.activeactor.EnemyAircraft} units based on the current state of the game.

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
                    newEnemy = new MovingBossAircraft(this, getScreenWidth(), newEnemyInitialYPosition);

                } else {
                    newEnemy = new MovingBossAircraft(this, getScreenWidth(), newEnemyInitialYPosition);
                }

                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Instantiates a new {@link LevelView} object, which is responsible for managing
     * the visual representation and UI elements for the level, such as {@link com.example.demo.userinterface.HeartDisplay}
     * and {@link com.example.demo.userinterface.WinImage}/{@link com.example.demo.userinterface.GameOverImage} images.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Determines whether the player has achieved the predefined number of kills required
     * to advance to the next stage or complete the level.
     * @return true if the player's number of kills is greater than or equal to the
     *         required kill target; false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
