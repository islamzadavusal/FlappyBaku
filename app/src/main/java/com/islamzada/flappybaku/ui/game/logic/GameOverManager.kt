package com.islamzada.flappybaku.ui.game.logic

import com.islamzada.flappybaku.ui.game.model.GameStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class GameOverManager(
    private val gameStatusLogic: GameStatusLogic, // Logic to manage game status
    private val onGameOverLogics: List<OnGameOverLogic>, // List of logics to execute on game over
    coroutineScope: CoroutineScope
) {
    init {
        // Launch a coroutine to monitor for game over state
        coroutineScope.launch {
            gameStatusLogic.gameState.filter {
                it == GameStatus.GameOver
            }.collect {
                // When game over state is detected, call each game over logic
                onGameOverLogics.forEach { it.onGameOver() }
            }
        }
    }
}