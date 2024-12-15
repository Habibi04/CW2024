package com.example.demo.userinterface;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ApplicationTest {

    protected static Stage testStage;

    @BeforeClass
    public static void setUp() {
        // Start the JavaFX application thread
        Platform.startup(() -> {
            testStage = new Stage();
            // Additional initialization if needed
        });
    }

    @AfterClass
    public static void tearDown() {
        Platform.exit();
    }

    public static Stage getTestStage() {
        return testStage;
    }
}