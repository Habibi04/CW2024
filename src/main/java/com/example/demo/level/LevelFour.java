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

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget())
            winGame();
    }

    @Override
    protected void winGame() {
        super.winGame();

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(3),
                event -> {
                    pauseGame();
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.show(getStage());
                }
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
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
                    newEnemy = new MovingBossAircraft(getScreenWidth(), newEnemyInitialYPosition);

                } else {
                    newEnemy = new MovingBossAircraft(getScreenWidth(), newEnemyInitialYPosition);
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
