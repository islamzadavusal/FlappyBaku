package com.islamzada.flappybaku.ui.game.logic

import androidx.compose.ui.unit.dp
import com.islamzada.flappybaku.ui.game.engine.GameLogic
import com.islamzada.flappybaku.ui.game.model.Pipe
import com.islamzada.flappybaku.ui.game.model.Player
import com.islamzada.flappybaku.ui.game.model.Viewport

class PlayerCollisionLogic(
    private val playerLogic: PlayerLogic, // Logic for player state
    private val blockMovementLogic: BlockMovementLogic, // Logic for block movements
    private val gameStatusLogic: GameStatusLogic, // Logic to manage game status
    private val viewport: Viewport
) : GameLogic {

    override fun onUpdate(deltaTime: Float) {
        // Check for collisions and boundaries
        if (checkPlayerOutOfBounds()) return
        checkBlockCollision()
    }

    // Check if the player is out of the visible area
    private fun checkPlayerOutOfBounds(): Boolean {
        val playerY = playerLogic.player.value.y
        if (playerY > viewport.height || playerY < (-50).dp) {
            gameStatusLogic.gameOver()
            return true
        }
        return false
    }

    // Check collisions between player and blocks
    private fun checkBlockCollision() {
        val player = playerLogic.player.value
        val blocks = blockMovementLogic.blockPosition.value
        blocks.forEach { block ->
            // Check for collisions with top and bottom pipes of blocks
            val topPipeCollided = collided(player, block.topPipe)
            val bottomPipeCollided = collided(player, block.bottomPipe)

            val collisionHappened = topPipeCollided || bottomPipeCollided

            if (collisionHappened) {
                gameStatusLogic.gameOver()
            }
        }
    }

    // Check if player collides with a given pipe
    private fun collided(
        player: Player,
        pipe: Pipe
    ) = player.rect.overlaps(pipe.rect)
}