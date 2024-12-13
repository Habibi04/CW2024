package com.example.demo.activeactor;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovingBossAircraftTest {

    private MovingBossAircraft movingBossAircraft;

    /**
     * Initialize the JavaFX runtime before all tests.
     */
    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {});
    }

    /**
     * Set up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize MovingBossAircraft with example position values
        movingBossAircraft = new MovingBossAircraft(null, 500, 100);
    }

    /**
     * Test that the initial setup of the MovingBossAircraft object is correct.
     */
    @Test
    void testInitialSetup() {
        assertEquals(500, movingBossAircraft.getLayoutX(), "Initial X position should be 500");
        assertEquals(100, movingBossAircraft.getLayoutY(), "Initial Y position should be 100");
        assertEquals(10, movingBossAircraft.getHealth(), "Initial health should be 10");
    }

    /**
     * Test the behavior of the fireProjectile method.
     */
    @Test
    void testFireProjectile() {
        boolean firedAtLeastOnce = false;

        // Attempt to fire a projectile several times to account for randomness
        for (int i = 0; i < 1000; i++) {
            ActiveActorDestructible projectile = movingBossAircraft.fireProjectile();
            if (projectile != null) {
                firedAtLeastOnce = true;
                assertTrue(projectile instanceof EnemyMissile, "Projectile should be of type EnemyMissile");
                break;
            }
        }

        assertTrue(firedAtLeastOnce, "MovingBossAircraft should fire a projectile within 1000 attempts.");
    }

    /**
     * Test that the projectile position is correctly offset based on the aircraft's position.
     */
    @Test
    void testFireProjectilePosition() {
        ActiveActorDestructible projectile = null;

        // Loop until a projectile is fired
        for (int i = 0; i < 1000; i++) {
            projectile = movingBossAircraft.fireProjectile();
            if (projectile != null) {
                break;
            }
        }

        assertNotNull(projectile, "Projectile should not be null when fired");

        // Verify the projectile's position
        assertEquals(400, projectile.getLayoutX(), "Projectile X position should be correctly offset");
        assertEquals(150, projectile.getLayoutY(), "Projectile Y position should be correctly offset");
    }



}
