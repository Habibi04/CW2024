package com.example.demo.activeactor;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for {@link PlayerAircraft}.
 */
class PlayerAircraftTest {

    private PlayerAircraft playerAircraft;

    @BeforeAll
    static void initJavaFX() {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        playerAircraft = new PlayerAircraft(100); // Initialize with 100 health
    }

    @Test
    void testInitialPosition() {
        assertEquals(5.0, playerAircraft.getLayoutX(), "Initial X position should be 5.0");
        assertEquals(300.0, playerAircraft.getLayoutY(), "Initial Y position should be 300.0");
    }

    @Test
    void testKillCount() {
        assertEquals(0, playerAircraft.getNumberOfKills(), "Initial kill count should be 0");

        playerAircraft.incrementKillCount();
        assertEquals(1, playerAircraft.getNumberOfKills(), "Kill count should increment");

        playerAircraft.decrementKillCount();
        assertEquals(0, playerAircraft.getNumberOfKills(), "Kill count should decrement");
    }

}
