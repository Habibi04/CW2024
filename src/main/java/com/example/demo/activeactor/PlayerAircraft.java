package com.example.demo.activeactor;

import com.example.demo.SoundManager;

public class PlayerAircraft extends FighterAircraft {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double X_LOWER_BOUND = 1000;
	private static final double X_UPPER_BOUND = 0;
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplierHorizontal;
	private int velocityMultiplierVertical;
	private int numberOfKills;

	public PlayerAircraft(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplierVertical = 0;
	}
	
	@Override
	public void updatePosition() {
		if (isMovingHorizontal()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * velocityMultiplierHorizontal);
			double newPosition = getLayoutX() + getTranslateX();
			if (newPosition < X_UPPER_BOUND || newPosition > X_LOWER_BOUND)
				this.setTranslateX(initialTranslateX);
		}

		if (isMovingVertical()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplierVertical);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		SoundManager.playSound("P1942_00004.wav");
		return new UserMissile(getTranslateX() + 128, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMovingHorizontal() {
		return velocityMultiplierHorizontal != 0;
	}

	private boolean isMovingVertical() {
		return velocityMultiplierVertical != 0;
	}

	public void moveUp() {
		velocityMultiplierVertical = -1;
	}

	public void moveDown() {
		velocityMultiplierVertical = 1;
	}

	public void moveLeft() {
		velocityMultiplierHorizontal = -1;
	}

	public void moveRight() {
		velocityMultiplierHorizontal = 1;
	}

	public void stopHorizontal() {
		velocityMultiplierHorizontal = 0;
	}

	public void stopVertical() {
		velocityMultiplierVertical = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}
	public void decrementKillCount() {
		numberOfKills--;
	}
}
