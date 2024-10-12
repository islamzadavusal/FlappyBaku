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
    private val blockMovementLogic: BlockMovementLogic,
    private val gameStatusLogic: GameStatusLogic,
    coroutineScope: CoroutineScope,
    viewport: Viewport
) {

    private val blockCreator = BlockCreator(viewport)

    init {
        coroutineScope.launch {
            gameStatusLogic.gameState.mapLatest { gameState ->
                if (gameState == GameStatus.Started) {
                    spawn()
                }
            }.collect {

            }
        }
    }

    private suspend fun spawn() {
        while (true) {
            val existingBlock = blockMovementLogic.blockPosition.value.firstOrNull {
                it.topPipe.x < blockDestructionPoint
            }
            val createBlock = blockCreator.createBlock()
            if (existingBlock != null) {
                val updatedBlock = existingBlock.copy(
                    hasBeenScored = false,
                    topPipe = createBlock.topPipe,
                    bottomPipe = createBlock.bottomPipe
                )
                blockMovementLogic.updateBlock(existingBlock, updatedBlock)
            } else {
                blockMovementLogic.addBlock(createBlock)
            }

            val randomTime = 1500 + (Random().nextFloat() * 1000)
            delay(randomTime.toLong())
        }
    }

    companion object {
        private val blockDestructionPoint = (-100).dp

    }
}