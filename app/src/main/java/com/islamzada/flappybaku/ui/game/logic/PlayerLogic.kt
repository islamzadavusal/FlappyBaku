package com.islamzada.flappybaku.ui.game.logic

import androidx.compose.ui.unit.dp
import com.islamzada.flappybaku.ui.game.engine.GameLogic
import com.islamzada.flappybaku.ui.game.model.Player
import com.islamzada.flappybaku.ui.game.model.Viewport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlayerLogic(
    private val gameStatusLogic: GameStatusLogic, // Dependency for managing the game's status
    viewport: Viewport // The viewport to get dimensions for positioning the player
) : GameLogic, OnGameOverLogic { // Implementing game logic and handling game over events

    private val defaultPlayer = Player(viewport.height / 2, 0f) // Setting the initial position of the player in the middle of the viewport
    private val _playerPosition = MutableStateFlow(defaultPlayer) // Mutable state to hold the current player position
    val player: StateFlow<Player> = _playerPosition // Exposing the player position as a read-only StateFlow

    // Function to update the player's position based on time elapsed
    override fun onUpdate(deltaTime: Float) {
        // Update the player's position using the formula for projectile motion
        _playerPosition.update { player ->
            // Calculate the new Y position using the player's speed and acceleration
            var newY = player.y + (player.speed * deltaTime + 0.5 * Player.acceleration * deltaTime * deltaTime).dp
            // Update the player's speed based on the acceleration
            var newSpeed = player.speed + Player.acceleration * deltaTime

            // Reset the player's position if it goes beyond a certain limit (2000.dp)
            if (newY > 2000.dp) {
                newY = 0.dp
                newSpeed = 0f
            }

            // Return a new Player instance with updated Y position and speed
            player.copy(y = newY, speed = newSpeed)
        }
    }

    // Function to handle player jumping
    fun jump() {
        // Notify that the game has started when the player jumps
        gameStatusLogic.gameStarted()
        // Update the player's speed to create a jump effect (negative speed)
        _playerPosition.update {
            it.copy(speed = -0.30f) // Setting a negative speed for jumping
        }
    }

    // Resetting player state when the game is over
    override fun onGameOver() {
        // Reset the player position to the default state
        _playerPosition.value = defaultPlayer
    }
}