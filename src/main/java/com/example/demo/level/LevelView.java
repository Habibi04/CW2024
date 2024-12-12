package com.example.demo.level;

import com.example.demo.activeactor.Boss;
import com.example.demo.userinterface.GameOverImage;
import com.example.demo.userinterface.HeartDisplay;
import com.example.demo.userinterface.WinImage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Manages the visual elements of a game level, including {@link HeartDisplay},
 * {@link WinImage} and {@link GameOverImage} images.
 */
public class LevelView {

	/** X-coordinate for positioning */
	private static final double HEART_DISPLAY_X_POSITION = 5;

	/** Y-coordinate for positioning */
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	/** X-coordinate for positioning */
	private static final int WIN_IMAGE_X_POSITION = 355;

	/** Y-coordinate for positioning */
	private static final int WIN_IMAGE_Y_POSITION = 175;

	/** X-coordinate for positioning */
	private static final int LOSS_SCREEN_X_POSITION = 0;

	/** Y-coordinate for positioning the {@link GameOverImage} screen */
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

		Text information = new Text("press escape to Pause");
		information.setFill(Color.WHITE);
		information.setFont(new Font("Arial",30));
		information.setX(500);
		information.setY(700);

		if(!root.getChildren().contains(information)){
			root.getChildren().add(information);
			information.toFront();
		}
	}

	/**
	 * Adds the {@link HeartDisplay} to the root group.
	 * Typically called to initialize the {@link com.example.demo.activeactor.PlayerAircraft} health visualization.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the {@link WinImage} on the screen.
	 * Invoked when the {@link com.example.demo.activeactor.PlayerAircraft} successfully completes the level.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the {@link GameOverImage} on the screen.
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