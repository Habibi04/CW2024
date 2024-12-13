package com.example.demo.activeactor;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for {@link EnemyAircraft}.
 */
class EnemyAircraftTest {

    private EnemyAircraft enemyAircraft;


    @BeforeAll
    static void initJavaFX() {

        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {

        enemyAircraft = new EnemyAircraft(null, 500, 100); // Assume the second argument is the X position and third is Y
    }

    @Test
    void testInitialSetup() {

        assertEquals(500, enemyAircraft.getLayoutX(), "Initial X position should be 500");
        assertEquals(100, enemyAircraft.getLayoutY(), "Initial Y position should be 100");
        assertEquals(1, enemyAircraft.getHealth(), "Initial health should be 1");
    }

    @Test
    void testFireProjectile() {

        boolean firedAtLeastOnce = false;
        for (int i = 0; i < 1000; i++) { // Simulate enough iterations to hit the FIRE_RATE probability
            ActiveActorDestructible projectile = enemyAircraft.fireProjectile();
            if (projectile != null) {
                firedAtLeastOnce = true;
                assertTrue(projectile instanceof EnemyMissile, "Projectile should be of type EnemyMissile");
                break;
            }
        }

        assertTrue(firedAtLeastOnce, "EnemyAircraft should fire a projectile with a sufficient number of attempts");
    }

    @Test
    void testFireProjectilePosition() {

        ActiveActorDestructible projectile = null;
        for (int i = 0; i < 1000; i++) {
            projectile = enemyAircraft.fireProjectile();
            if (projectile != null) {
                break;
            }
        }

        assertNotNull(projectile, "Projectile should not be null when fired");
        assertEquals(400, projectile.getLayoutX(), "Projectile X position should be correctly offset");
        assertEquals(150, projectile.getLayoutY(), "Projectile Y position should be correctly offset");
    }
}
