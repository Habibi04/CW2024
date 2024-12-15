package com.example.demo;

import com.example.demo.activeactor.PlayerAircraft;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The {@link InputManager} class is a singleton responsible for handling keyboard inputs
 * to control the {@link PlayerAircraft} and trigger game-related actions. It maps specific
 * key presses and releases to {@link PlayerAircraft} movements and other functionality.
 */
public class InputManager {

    /** The single instance of the {@link InputManager}. */
    static InputManager instance;

    /** The {@link PlayerAircraft} object controlled by the user. */
    private PlayerAircraft user;

    /** A {@link Runnable} representing an action to be triggered (e.g., shooting). */
    private Runnable runnable;

    /**
     * Retrieves the singleton instance of the {@link InputManager}.
     *
     * @return the {@link InputManager} instance.
     */
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    /**
     * Initializes the {@link InputManager} with the required background node, {@link PlayerAircraft},
     * and action to be triggered.
     *
     * @param background the {@link Node} receiving key events (e.g., the game background).
     * @param user the {@link PlayerAircraft} controlled by the user.
     * @param runnable a {@link Runnable} for executing an action (e.g., shooting).
     */
    public void initialize(Node background, PlayerAircraft user, Runnable runnable) {
        this.user = user;
        this.runnable = runnable;
        initializeBackgroundInputs(background);
    }

    /**
     * Configures the key press and key release event handlers for the specified background node.
     *
     * @param background the {@link Node} receiving key events.
     */
    private void initializeBackgroundInputs(Node background) {
        background.setOnKeyPressed(this::handleKeyPress);
        background.setOnKeyReleased(this::handleKeyRelease);
    }

    /**
     * Handles key press events to perform {@link PlayerAircraft} movements or trigger actions.
     *
     * @param e the {@link KeyEvent} representing the key press.
     */
    private void handleKeyPress(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == KeyCode.UP) user.moveUp();
        if (kc == KeyCode.DOWN) user.moveDown();
        if (kc == KeyCode.LEFT) user.moveLeft();
        if (kc == KeyCode.RIGHT) user.moveRight();
        if (kc == KeyCode.SPACE) runnable.run();
    }

    /**
     * Handles key release events to stop {@link PlayerAircraft} movements when keys are released.
     *
     * @param e the {@link KeyEvent} representing the key release.
     */
    private void handleKeyRelease(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stopVertical();
        if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) user.stopHorizontal();
    }
}
