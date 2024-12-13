# DMS Coursework 2024
## Mohammed Ahnaf Habib 
## Student ID 20511215
## [Github Repository link](https://github.com/Habibi04/CW2024)

***
## Compilation Instructions

***
To compile and run the project, follow these steps:

1. Ensure Java (JDK 19 or above) & Maven is installed on your system.
2. Additional dependency : javafx media .
3. Open the project in intellij and run Main.java

****
****
## Newly Implemented & Working Features:
***
- MAIN MENU SCREEN

1. Start Game Button
2. Exit Button

- PAUSE MENU SCREEN

1. Press Esc to Access
2. Resume Button
3. Restart Button
4. Exit Button

- SOUND MANAGER

1. Sound effects for user fire
2. Sound effects for enemy destruction
3. Sound effects for GAME OVER
4. Sound effects for WIN GAME
5. Sound effects for GAME LEVELS

- LEVEL DISPLAY
1. Shows the level number

- EXTRA LEVELS

1. Level Three
2. Level Four

- HORIZONTAL MOVEMENT OF PLAYER AIRCRAFT 

***
## Features Not Working:

- Shield effect for the enemy projectile
***
## Features Not Implemented:

### 1. Endless game mode : 
- opted for additional levels instead of this.
 
### 2. Power ups, total kills, total hits, moving background:
- Would require major refactoring or the whole code will fail.

### 4. Obstacles : 
- Didn't have a concrete idea to implement.
***
## NEW JAVA CLASSES:

### 1. MovingBoss Aircraft :

- An enemy aircraft class that implements a horizontally moving boss enemy with these key features:

### 2. Level Three :

- Third game stage with 4 player health points
- Requires 15 kills to advance to LevelFour

### 3. Level Four :


- Uses the `MovingBossAircraft` class
- Pauses for 3 seconds before transitioning to the main menu on win.


### 4. Main Menu :

- The MainMenu class represents the game's main menu with options to start the game or exit

### 5. Pause Menu :

- The PauseMenu class represents the game's pause menu with options to resume,restart the game or end game.

### 6. Input Manager :

- The InputManager class is a singleton that handles keyboard inputs to control the PlayerAircraft

### 7. Sound Manager :

- The SoundManager class handles sound effects and background music:

***
## Modified JAVA CLASSES:

### 1. ActiveActor:

- Added comprehensive Javadoc comments to improve code documentation
- Enhanced class and method descriptions
- Clarified the purpose and functionality of each method through detailed documentation
- No functional code changes were made
- Improved code readability and maintainability through descriptive comments

### 2. BossMissile:

- Renamed class from BossProjectile to BossMissile to improve naming clarity

### 3. ActiveActorDestructible:

- Moved class to a new package structure: com.example.demo.activeactor

### 4. FighterAircraft:

- Renamed class from `FighterPlane` to `FighterAircraft` for improved clarity
- Moved class to new package structure: `com.example.demo.activeactor`
- Added sound effect (`SoundManager.playSound()`) when aircraft is destroyed
- Improved method and variable descriptions

### 5. PlayerAircraft:

- Renamed class from `UserPlane` to `PlayerAircraft`
- Migrated class to new package: `com.example.demo.activeactor`
- Added comprehensive Javadoc comments to enhance code documentation
- Expanded movement functionality with separate horizontal and vertical velocity multipliers
- Added sound effects for firing missiles
- Added methods for decrementing kill count

### 6. UserMissile:

- tweaked the parameters.

### 7. EnemyAircraft:

- Renamed class from `EnemyPlane` to `EnemyAircraft`
- Migrated class to new package: `com.example.demo.activeactor`
- Added `LevelParent` integration for projectile spawning
- Modified `updateActor()` to use `levelParent.spawnEnemyProjectile()`
- Added level context management
- Changed `EnemyProjectile` to `EnemyMissile`

### 8. Missile:

- Renamed class from `Projectile` to `Missile`
- Migrated class to new package: `com.example.demo.activeactor`
### 9. EnemyMissile:

- changed some parameters.

### 10. Boss:

- Introduced the `ShieldImage` class for visual representation of the Boss's shield.
- Linked `ShieldImage` updates with the Boss's shield activation and deactivation mechanics.
- Renamed `FighterPlane` to `FighterAircraft` for improved clarity.
- Modified shield activation probability (`BOSS_SHIELD_PROBABILITY`) and health (`HEALTH`) to balance gameplay.
- Added integration with `LevelParent` for managing Boss projectiles dynamically.
- Adjusted sprite dimensions and positions for better alignment with game design.

### 11. Main:

- Here’s a brief description of the modifications for your `README.md`:
- Replaced the direct initialization of `Controller` in `Main` with integration of a new `MainMenu` class to display the initial menu.
- Simplified the `start` method by focusing on the `MainMenu` setup.
- Added descriptions for constants like `SCREEN_WIDTH`, `SCREEN_HEIGHT`, and `TITLE`.

### 12. Controller:

- Refactored the `Controller` class to include more structured management of levels using a `PauseMenu` and `SoundManager`.
- The updated `goToLevel` method now initializes the `PauseMenu` with callbacks for game resumption, main menu navigation, and level restart.
- Introduced a `restartCurrentLevel` method to allow seamless reloading of the active level.
- Added an `ESCAPE` key event handler to toggle the `PauseMenu` during gameplay, enhancing user interactivity.
- Incorporated the `PauseMenu` for pausing and resuming gameplay, switching to the main menu, or restarting the current level.
- Improved error handling for level transitions with user-facing alerts.
- Added calls to `SoundManager` to play background music during level transitions, enhancing the game's auditory experience.


### 13. LevelParent:

- Implemented a unified parent class for all levels to standardize shared functionality.
- Moved common game logic (e.g., player interactions, environment setup) into the parent class.

### 14. LevelTwo:

- Added next level transition: Replaced winning condition with progression to `LevelThree`
- Enhanced Boss initialization: Boss constructor now takes the level as a parameter
- Added Boss shield functionality: Shield image now appears with the boss spawn
- Reorganized package structure: Moved class to `com.example.demo.level` package

***
## Unexpected Problems:

1. The game kept playing in the background even though the Pause Menu class was invoked.Fixed by adding a pause game function in the level parent.
2. player aircraft fires projectile even though the game is over.Called Pause game function in level parent.
3. player aircraft movement continues automatically if game is paused while moving the aircraft and then resumed.Called the PlayerAircraft.stopVertical and stopHorizontal when the game is paused.
