package com.islamzada.flappybaku.ui.game.logic

import androidx.compose.ui.unit.dp
import com.islamzada.flappybaku.ui.game.model.GameStatus
import com.islamzada.flappybaku.ui.game.model.Viewport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import java.util.Random

@OptIn(ExperimentalCoroutinesApi::class)
class BlockSpawnerLogic(
    private val blockMovementLogic: BlockMovementLogic, // Logic to handle block movement
    private val gameStatusLogic: GameStatusLogic, // Logic to manage game status (started, over, etc.)
    coroutineScope: CoroutineScope,
    viewport: Viewport
) {

    private val blockCreator = BlockCreator(viewport)

    init {
        // Launch a coroutine to monitor the game state
        coroutineScope.launch {
            gameStatusLogic.gameState.mapLatest { gameState ->

                if (gameState == GameStatus.Started) {
                    spawn()
                }
            }.collect {
            }
        }
    }

    // Function to spawn blocks at intervals
    private suspend fun spawn() {
        while (true) {
            // Check for existing blocks within a certain destruction point
            val existingBlock = blockMovementLogic.blockPosition.value.firstOrNull {
                it.topPipe.x < blockDestructionPoint
            }
            val createBlock = blockCreator.createBlock()
            if (existingBlock != null) {
                // If there is an existing block, update it with new data
                val updatedBlock = existingBlock.copy(
                    hasBeenScored = false,
                    topPipe = createBlock.topPipe,
                    bottomPipe = createBlock.bottomPipe
                )
                blockMovementLogic.updateBlock(existingBlock, updatedBlock)
            } else {
                // If no existing block is found, add the new block
                blockMovementLogic.addBlock(createBlock)
            }

            // Random delay between block spawns
            val randomTime = 1500 + (Random().nextFloat() * 1000)
            delay(randomTime.toLong())
        }
    }

    // Companion object to define constant values
    companion object {
        private val blockDestructionPoint = (-100).dp
    }
}