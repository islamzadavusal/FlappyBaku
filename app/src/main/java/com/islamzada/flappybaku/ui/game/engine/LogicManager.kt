package com.islamzada.flappybaku.ui.game.engine

import com.islamzada.flappybaku.ui.game.logic.GameStatusLogic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// LogicManager class is responsible for managing updates of all game logics.
class LogicManager(
    private val gameLogics: List<GameLogic>,
    private val gameStatusLogic: GameStatusLogic,
    private val timeManager: TimeManager,
    coroutineScope: CoroutineScope
) {
    // Initializes a coroutine to update the game logic continuously as time progresses.
    init {
        coroutineScope.launch {
            timeManager.deltaTime.collect { deltaTime ->
                if (!gameStatusLogic.isStarted()) return@collect

                gameLogics.forEach { it.onUpdate(deltaTime) }
            }
        }
    }
}