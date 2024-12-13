package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * The {@link SoundManager} class manages the playback of sound effects and background music
 * for the application. It provides methods to play specific sounds, start background music,
 * and stop the background music.
 */
public class SoundManager {

    /** The {@link MediaPlayer} instance used for playing the background music. */
    private static final MediaPlayer backgroundSound = new MediaPlayer(
            new Media(SoundManager.class.getResource("/com/example/demo/sounds/P1942_00000.wav").toExternalForm())
    );

    /**
     * Plays a specified sound effect.
     *
     * @param soundName the name of the sound file (e.g., "sound.wav") to be played.
     *                  The file should be located in the {/com/example/demo/sounds/} directory.
     */
    public static void playSound(String soundName) {
        MediaPlayer sound = new MediaPlayer(
                new Media(SoundManager.class.getResource("/com/example/demo/sounds/" + soundName).toExternalForm())
        );
        sound.setVolume(0.025);
        sound.play();
    }

    /**
     * Starts playing the background music in a loop. If the music ends, it will automatically
     * restart from the beginning.
     */
    public static void playBackgroundSound() {
        backgroundSound.setVolume(0.025);

        backgroundSound.setOnEndOfMedia(() -> {
            backgroundSound.seek(Duration.ZERO); // Restart the music from the beginning.
            backgroundSound.play();
        });

        backgroundSound.play();
    }

    /**
     * Stops the background music if it is currently playing.
     */
    public static void stopBackgroundSound() {
        backgroundSound.stop();
    }
}
