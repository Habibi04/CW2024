package com.example.demo.level;

import com.example.demo.userinterface.HeartDisplay;
import com.example.demo.userinterface.ShieldImage;
import javafx.scene.Group;

/**
 * Represents the view for {@link LevelTwo} in the game, extending the base {@link LevelViewLevelTwo}.
 *
 * This class specializes the {@link LevelViewLevelTwo} for the {@link LevelTwo}, adding
 * specific visual elements such as a {@link ShieldImage} unique to this level.
 */
public class LevelViewLevelTwo extends LevelView {

	/** X-coordinate for positioning */
	private static final int SHIELD_X_POSITION = 1150;

	/** Y-coordinate for positioning */
	private static final int SHIELD_Y_POSITION = 500;

	/** The root group to which game elements are added */
	private final Group root;

	/** Image representing the shield in {@link LevelTwo} */
	private final ShieldImage shieldImage;

	/**
	 * Constructs a new {@link LevelViewLevelTwo} with specified root and initial hearts.
	 *
	 * Initializes the base {@link LevelView} and adds a shield image specific to {@link LevelTwo}.
	 *
	 * @param root The JavaFX group to which game elements will be added
	 * @param heartsToDisplay The initial number of hearts to display for the {@link com.example.demo.activeactor.PlayerAircraft}
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}
}