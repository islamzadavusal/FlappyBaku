package com.islamzada.flappybaku.ui.game.logic

import androidx.compose.ui.unit.dp
import com.islamzada.flappybaku.ui.game.engine.GameLogic
import com.islamzada.flappybaku.ui.game.model.Pipe
import com.islamzada.flappybaku.ui.game.model.Player
import com.islamzada.flappybaku.ui.game.model.Viewport

class PlayerCollisionLogic(
    private val playerLogic: PlayerLogic,
    private val blockMovementLogic: BlockMovementLogic,
    private val gameStatusLogic: GameStatusLogic,
    private val viewport: Viewport
) : GameLogic {

    override fun onUpdate(deltaTime: Float) {
        if (checkPlayerOutOfBounds()) return
        checkBlockCollision()
    }

    private fun checkPlayerOutOfBounds(): Boolean {
        val playerY = playerLogic.player.value.y
        if (playerY > viewport.height || playerY < (-50).dp) {
            gameStatusLogic.gameOver()
            return true
        }
        return false
    }

    private fun checkBlockCollision() {
        val player = playerLogic.player.value
        val blocks = blockMovementLogic.blockPosition.value
        blocks.forEach { block ->
            val topPipeCollided = collided(player, block.topPipe)
            val bottomPipeCollided = collided(player, block.bottomPipe)

            val collisionHappened = topPipeCollided || bottomPipeCollided

            if (collisionHappened) {
                gameStatusLogic.gameOver()
            }
        }
    }

    private fun collided(
        player: Player,
        pipe: Pipe
    ) = player.rect.overlaps(pipe.rect)
}