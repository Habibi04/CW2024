package com.example.demo;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {
    private static InputManager instance;

    private UserPlane user; // Assuming User is a class that has movement methods
    private Runnable runnable;

    // Private constructor to prevent instantiation
    private InputManager(Node background, UserPlane user, Runnable runnable) {
        this.user = user;
        this.runnable = runnable;
        initializeBackgroundInputs(background);
    }

    public static InputManager getInstance(Node background, UserPlane user, Runnable runnable) {
        if (instance == null) {
            instance = new InputManager(background, user, runnable);
        }
        return instance;
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
