package com.example.demo.level;

import com.example.demo.userinterface.GameOverImage;
import com.example.demo.userinterface.HeartDisplay;
import com.example.demo.userinterface.WinImage;
import javafx.scene.Group;

/**
 * Manages the visual elements of a game level, including heart displays,
 * win and game over images.
 */
public class LevelView {

	/** X-coordinate for positioning the heart display */
	private static final double HEART_DISPLAY_X_POSITION = 5;

	/** Y-coordinate for positioning the heart display */
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	/** X-coordinate for positioning the win image */
	private static final int WIN_IMAGE_X_POSITION = 355;

	/** Y-coordinate for positioning the win image */
	private static final int WIN_IMAGE_Y_POSITION = 175;

	/** X-coordinate for positioning the game over screen */
	private static final int LOSS_SCREEN_X_POSITION = 0;

	/** Y-coordinate for positioning the game over screen */
	private static final int LOSS_SCREEN_Y_POSISITION = 0;

	/** The root group to which game elements are added */
	private final Group root;

	/** Image displayed when the {@link com.example.demo.activeactor.PlayerAircraft} wins the level */
	private final WinImage winImage;

	/** Image displayed when the {@link com.example.demo.activeactor.PlayerAircraft} loses the game */
	private final GameOverImage gameOverImage;

	/** Display showing the {@link com.example.demo.activeactor.PlayerAircraft} remaining hearts/health */
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a new {@link LevelView} with specified root and initial hearts.
	 * @param root The JavaFX group to which game elements will be added
	 * @param heartsToDisplay The initial number of hearts to display for the {@link com.example.demo.activeactor.PlayerAircraft}
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
	}

	/**
	 * Adds the heart display to the root group.
	 * Typically called to initialize the {@link com.example.demo.activeactor.PlayerAircraft} health visualization.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen.
	 * Invoked when the {@link com.example.demo.activeactor.PlayerAircraft} successfully completes the level.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game over image on the screen.
	 * Called when the {@link com.example.demo.activeactor.PlayerAircraft} fails to complete the level.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Removes hearts from the display based on the remaining health.
	 *
	 * @param heartsRemaining The number of hearts the {@link com.example.demo.activeactor.PlayerAircraft} has left
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
}