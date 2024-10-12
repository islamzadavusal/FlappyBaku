package com.islamzada.flappybaku.ui.game.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.islamzada.flappybaku.ui.game.engine.LogicManager
import com.islamzada.flappybaku.ui.game.engine.TimeManager
import com.islamzada.flappybaku.ui.game.engine.gameCoroutineScope
import com.islamzada.flappybaku.ui.game.logic.BlockSpawnerLogic
import com.islamzada.flappybaku.ui.game.logic.BlockMovementLogic
import com.islamzada.flappybaku.ui.game.logic.GameOverManager
import com.islamzada.flappybaku.ui.game.logic.GameScoreLogic
import com.islamzada.flappybaku.ui.game.logic.GameStatusLogic
import com.islamzada.flappybaku.ui.game.logic.OnGameOverLogic
import com.islamzada.flappybaku.ui.game.logic.PlayerCollisionLogic
import com.islamzada.flappybaku.ui.game.logic.PlayerLogic
import com.islamzada.flappybaku.ui.game.model.Viewport

class GameDI(viewport: Viewport, val timeManager: TimeManager) {
    private val coroutineScope = gameCoroutineScope()
    val gameStatusLogic = GameStatusLogic(coroutineScope)
    val playerLogic = PlayerLogic(gameStatusLogic, viewport)
    val blockMovementLogic = BlockMovementLogic(viewport)
    private val playerCollisionLogic =
        PlayerCollisionLogic(playerLogic, blockMovementLogic, gameStatusLogic, viewport)
    val gameScoreLogic = GameScoreLogic(playerLogic, blockMovementLogic)
    private val onGameOverLogics: List<OnGameOverLogic> =
        listOf(playerLogic, blockMovementLogic, gameScoreLogic)
    val gameOverManager = GameOverManager(gameStatusLogic, onGameOverLogics, coroutineScope)
    private val logics = listOf(
        playerLogic,
        blockMovementLogic,
        playerCollisionLogic,
        gameScoreLogic,
        gameStatusLogic,
    )
    val logicManager = LogicManager(logics, gameStatusLogic, timeManager, coroutineScope)

    val blockSpawnerLogic =
        BlockSpawnerLogic(blockMovementLogic, gameStatusLogic, coroutineScope, viewport)

    companion object {
        @Composable
        fun rememberDI(viewport: Viewport): GameDI {
            val coroutineScope = rememberCoroutineScope()
            val timeManager = remember {
                TimeManager(coroutineScope)
            }
            return remember {
                GameDI(viewport, timeManager)
            }
        }
    }
}