package com.example.demo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    public static void playSound(String soundName) {
        MediaPlayer sound = new MediaPlayer(new Media(SoundManager.class.getResource("/com/example/demo/sounds/" + soundName).toExternalForm()));
        sound.play();
    }
}
