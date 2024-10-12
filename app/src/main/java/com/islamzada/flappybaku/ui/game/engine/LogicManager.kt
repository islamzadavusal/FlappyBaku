package com.islamzada.flappybaku.ui.game.engine

import com.islamzada.flappybaku.ui.game.logic.GameStatusLogic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LogicManager(
    private val gameLogics: List<GameLogic>,
    private val gameStatusLogic: GameStatusLogic,
    private val timeManager: TimeManager,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            timeManager.deltaTime.collect { deltaTime ->
                if (!gameStatusLogic.isStarted()) return@collect

                gameLogics.forEach { it.onUpdate(deltaTime) }
            }
        }
    }
}