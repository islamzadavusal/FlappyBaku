package com.islamzada.flappybaku.ui.game.logic

import com.islamzada.flappybaku.ui.game.engine.GameLogic
import com.islamzada.flappybaku.ui.game.model.GameStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class) // Opt-in for experimental coroutine APIs
class GameStatusLogic(coroutineScope: CoroutineScope) : GameLogic {
    private val _gameState = MutableStateFlow(GameStatus.NotStarted) // Initial game state
    val gameState: StateFlow<GameStatus> = _gameState // Expose game state as StateFlow

    init {
        // Launch a coroutine to restart the game after a delay when the game is over
        coroutineScope.launch {
            gameState.mapLatest {
                if (it == GameStatus.GameOver) {
                    delay(2000)
                    restartGame()
                }
            }.collect()
        }
    }

    override fun onUpdate(deltaTime: Float) {
        // Logic to update game state (not implemented)
    }

    // Method to start the game
    fun gameStarted() {
        _gameState.update { GameStatus.Started }
    }

    // Restart the game logic
    private fun restartGame() {
        _gameState.update { GameStatus.NotStarted }
    }

    // Check if the game has started
    fun isStarted(): Boolean {
        return _gameState.value == GameStatus.Started
    }

    // Method to set game over state
    fun gameOver() {
        _gameState.update { GameStatus.GameOver }
    }
}