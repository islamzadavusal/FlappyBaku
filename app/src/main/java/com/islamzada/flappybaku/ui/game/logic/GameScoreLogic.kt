package com.islamzada.flappybaku.ui.game.logic

import com.islamzada.flappybaku.ui.game.engine.GameLogic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameScoreLogic(
    private val playerLogic: PlayerLogic, // Logic to manage player state
    private val blockMovementLogic: BlockMovementLogic // Logic to manage block movements
) : GameLogic, OnGameOverLogic {
    private val _score = MutableStateFlow(0) // State flow to track the score
    val score: StateFlow<Int> = _score // Expose score as a read-only StateFlow

    override fun onUpdate(deltaTime: Float) {
        val blocks = blockMovementLogic.blockPosition.value
        blocks.forEach { block ->
            if (block.hasBeenScored) return@forEach

            val scoreRect = block.scoreRect
            val playerRect = playerLogic.player.value.rect

            // Check for overlap (scoring)
            val hasScored = playerRect.overlaps(scoreRect)
            if (hasScored) {
                blockMovementLogic.scoreBlock(block)
                _score.update { it + 1 }
            }
        }
    }

    // Reset score on game over
    override fun onGameOver() {
        _score.value = 0
    }
}