package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {
    private static final MediaPlayer backgroundSound = new MediaPlayer(new Media(SoundManager.class.getResource("/com/example/demo/sounds/P1942_00000.wav").toExternalForm()));

    public static void playSound(String soundName) {
        MediaPlayer sound = new MediaPlayer(new Media(SoundManager.class.getResource("/com/example/demo/sounds/" + soundName).toExternalForm()));
        sound.play();
    }

    public static void playBackgroundSound() {
        backgroundSound.setOnEndOfMedia(() -> {
            backgroundSound.seek(Duration.ZERO);
            backgroundSound.play();
        });

        backgroundSound.play();
    }

    public static void stopBackgroundSound() {
        backgroundSound.stop();
    }
}
