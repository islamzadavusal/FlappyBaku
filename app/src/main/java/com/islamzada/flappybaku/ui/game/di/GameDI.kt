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

// GameDI class is responsible for setting up all the dependencies needed to run the game.
class GameDI(viewport: Viewport, val timeManager: TimeManager) {
    // Creates a CoroutineScope for managing asynchronous tasks in the game.
    private val coroutineScope = gameCoroutineScope()

    // GameStatusLogic manages the current state of the game (e.g., Started, GameOver).
    val gameStatusLogic = GameStatusLogic(coroutineScope)

    // PlayerLogic manages the player's movement and behavior in the game.
    val playerLogic = PlayerLogic(gameStatusLogic, viewport)

    // BlockMovementLogic controls the movement of obstacles (blocks) on the screen.
    val blockMovementLogic = BlockMovementLogic(viewport)

    // PlayerCollisionLogic checks if the player has collided with blocks or gone out of bounds.
    private val playerCollisionLogic =
        PlayerCollisionLogic(playerLogic, blockMovementLogic, gameStatusLogic, viewport)

    // GameScoreLogic handles the game’s score by detecting when the player successfully passes obstacles.
    val gameScoreLogic = GameScoreLogic(playerLogic, blockMovementLogic)

    // List of logics that need to be reset when the game is over.
    private val onGameOverLogics: List<OnGameOverLogic> =
        listOf(playerLogic, blockMovementLogic, gameScoreLogic)

    // GameOverManager handles the logic for when the game ends.
    val gameOverManager = GameOverManager(gameStatusLogic, onGameOverLogics, coroutineScope)

    // List of all game logics managed by LogicManager.
    private val logics = listOf(
        playerLogic,
        blockMovementLogic,
        playerCollisionLogic,
        gameScoreLogic,
        gameStatusLogic,
    )

    // LogicManager updates all the game logic based on time changes.
    val logicManager = LogicManager(logics, gameStatusLogic, timeManager, coroutineScope)

    // BlockSpawnerLogic is responsible for spawning new obstacles.
    val blockSpawnerLogic =
        BlockSpawnerLogic(blockMovementLogic, gameStatusLogic, coroutineScope, viewport)

    companion object {
        // rememberDI provides a Composable function to create and remember an instance of GameDI.
        @Composable
        fun rememberDI(viewport: Viewport): GameDI {
            val coroutineScope = rememberCoroutineScope()
            val timeManager = remember { TimeManager(coroutineScope) }
            return remember { GameDI(viewport, timeManager) }
        }
    }
}