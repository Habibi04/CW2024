package com.example.demo;

import com.example.demo.activeactor.PlayerAircraft;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {
    private static InputManager instance;

    private PlayerAircraft user;
    private Runnable runnable;

    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    public void initialize(Node background, PlayerAircraft user, Runnable runnable) {
        this.user = user;
        this.runnable = runnable;
        initializeBackgroundInputs(background);
    }

    private void initializeBackgroundInputs(Node background) {
        background.setOnKeyPressed(e -> handleKeyPress(e));
        background.setOnKeyReleased(e -> handleKeyRelease(e));
    }

    private void handleKeyPress(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == KeyCode.UP) user.moveUp();
        if (kc == KeyCode.DOWN) user.moveDown();
        if (kc == KeyCode.LEFT) user.moveLeft();
        if (kc == KeyCode.RIGHT) user.moveRight();
        if (kc == KeyCode.SPACE) runnable.run();
    }

    private void handleKeyRelease(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stopVertical();
        if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) user.stopHorizontal();
    }
}
