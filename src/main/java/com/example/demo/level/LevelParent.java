package com.example.demo.level;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.SoundManager;
import com.example.demo.activeactor.ActiveActorDestructible;
import com.example.demo.InputManager;
import com.example.demo.activeactor.PlayerAircraft;
import com.example.demo.userinterface.MainMenu;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Abstract base class for managing levels in the game. Handles core game mechanics,
 * such as spawning units, collision detection, game state transitions, and UI updates.
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final PlayerAircraft user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private Stage stage;
	private int currentNumberOfEnemies;
	private LevelView levelView;
	private boolean isPaused;

	/**
	 * Retrieves the stage associated with the level.
	 *
	 * @return the current stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Sets the stage for the level.
	 *
	 * @param stage the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Constructs a new {@link LevelParent} instance.
	 *
	 * @param backgroundImageName the name of the background image file
	 * @param screenHeight        the height of the screen
	 * @param screenWidth         the width of the screen
	 * @param playerInitialHealth the initial health of the {@link PlayerAircraft}
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new PlayerAircraft(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.isPaused = false;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	/**
	 * Pauses the game by stopping the timeline.
	 */
	public void pauseGame() {
		timeline.pause();
		user.stopHorizontal();
		user.stopVertical();
		isPaused = true;
	}

	/**
	 * Resumes the game by playing the timeline.
	 */
	public void resumeGame() {
		timeline.play();
		isPaused = false;
	}

	/**
	 * Abstract method to initialize friendly units. Must be implemented by subclasses.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Abstract method to check if the game is over. Must be implemented by subclasses.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method to spawn enemy units. Must be implemented by subclasses.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method to instantiate the level view. Must be implemented by subclasses.
	 *
	 * @return the instantiated {@link LevelView}
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes and returns the scene for the level.
	 *
	 * @return the initialized scene
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeLevelText();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the game by requesting focus and playing the timeline.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Transitions to the next level.
	 *
	 * @param levelName the name of the next level
	 */
	public void goToNextLevel(String levelName) {
		timeline.stop();
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Updates the scene by handling game logic, such as spawning enemies, collision detection,
	 * and UI updates.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Initializes the game loop timeline.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background for the level.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		InputManager.getInstance().initialize(background, user, this::fireProjectile);
		root.getChildren().add(background);
		background.toBack();
	}

	/**
	 * Initializes the level text display.
	 */
	private void initializeLevelText() {
		String levelName = getClass().toString().split("\\.")[4];
		levelName = levelName.substring(0, 5) + " " + levelName.substring(5);

		Label label = new Label(levelName);
		label.setTranslateX(stage.getWidth() / 2);
		label.setTranslateY(32);
		label.setScaleX(4);
		label.setScaleY(4);
		root.getChildren().add(label);
	}

	/**
	 * Fires a {@link com.example.demo.activeactor.UserMissile} from the {@link PlayerAircraft}.
	 */
	private void fireProjectile() {
		if (!isPaused) {
			ActiveActorDestructible projectile = user.fireProjectile();
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
		}
	}

	/**
	 * Spawns an {@link com.example.demo.activeactor.EnemyMissile} in the scene.
	 *
	 * @param projectile the projectile to spawn
	 */
	public void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors in the scene.
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes all destroyed actors from the scene.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from the specified list and the scene.
	 *
	 * @param actors the list of actors to process
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between planes.
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between {@link com.example.demo.activeactor.UserMissile} and {@link com.example.demo.activeactor.EnemyAircraft}.
	 */
	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between {@link com.example.demo.activeactor.EnemyMissile} and {@link PlayerAircraft}.
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Detects and processes collisions between two groups of actors.
	 *
	 * @param actors1 the first group of actors
	 * @param actors2 the second group of actors
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	/**
	 * Handles scenarios where {@link com.example.demo.activeactor.EnemyAircraft} penetrate defenses.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
				user.decrementKillCount();
			}
		}
	}

	/**
	 * Updates the level view based on the {@link PlayerAircraft} current health.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the {@link PlayerAircraft}'s kill count based on defeated {@link com.example.demo.activeactor.EnemyAircraft}.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Checks if an {@link com.example.demo.activeactor.EnemyAircraft} has penetrated the {@link PlayerAircraft}'s defenses.
	 *
	 * @param enemy the enemy actor to check
	 * @return true if the enemy has penetrated, false otherwise
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Handles the winning scenario by stopping the timeline and showing the {@link com.example.demo.userinterface.WinImage}.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		SoundManager.playSound("P1942_00014.wav");
		SoundManager.stopBackgroundSound();
	}

	/**
	 * Handles the losing scenario by stopping the timeline, showing the {@link com.example.demo.userinterface.GameOverImage},
	 * and navigating back to the {@link MainMenu} after a delay.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		SoundManager.playSound("P1942_00014.wav");
		SoundManager.stopBackgroundSound();

		pauseGame();
		System.out.println(timeline.getStatus());

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
	 * Retrieves the {@link PlayerAircraft}.
	 *
	 * @return the {@link PlayerAircraft}.
	 */
	protected PlayerAircraft getUser() {
		return user;
	}

	/**
	 * Retrieves the root group of the scene.
	 *
	 * @return the root group
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Retrieves the current number of {@link com.example.demo.activeactor.EnemyAircraft} in the scene.
	 *
	 * @return the number of {@link com.example.demo.activeactor.EnemyAircraft}
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an {@link com.example.demo.activeactor.EnemyAircraft} unit to the scene.
	 *
	 * @param enemy the {@link com.example.demo.activeactor.EnemyAircraft} unit to add
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Retrieves the maximum Y position .
	 *
	 * @return the maximum Y position .
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Retrieves the width of the screen.
	 *
	 * @return the screen width
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the {@link PlayerAircraft} is destroyed.
	 *
	 * @return true if the {@link PlayerAircraft} is destroyed, false otherwise
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the current number of  {@link com.example.demo.activeactor.EnemyAircraft} in the scene.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
}
