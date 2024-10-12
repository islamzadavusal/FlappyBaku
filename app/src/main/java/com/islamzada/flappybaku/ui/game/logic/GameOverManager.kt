package com.islamzada.flappybaku.ui.game.logic

import com.islamzada.flappybaku.ui.game.model.GameStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class GameOverManager(
    private val gameStatusLogic: GameStatusLogic,
    private val onGameOverLogics: List<OnGameOverLogic>,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            gameStatusLogic.gameState.filter {
                it == GameStatus.GameOver
            }.collect {
                onGameOverLogics.forEach { it.onGameOver() }
            }
        }
    }
}