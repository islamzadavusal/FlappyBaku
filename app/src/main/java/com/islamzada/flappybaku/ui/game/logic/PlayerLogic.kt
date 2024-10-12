package com.islamzada.flappybaku.ui.game.logic

import androidx.compose.ui.unit.dp
import com.islamzada.flappybaku.ui.game.engine.GameLogic
import com.islamzada.flappybaku.ui.game.model.Player
import com.islamzada.flappybaku.ui.game.model.Viewport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlayerLogic(
    private val gameStatusLogic: GameStatusLogic,
    viewport: Viewport
) :
    GameLogic, OnGameOverLogic {
    private val defaultPlayer = Player(viewport.height / 2, 0f)
    private val _playerPosition = MutableStateFlow(defaultPlayer)
    val player: StateFlow<Player> = _playerPosition


    override fun onUpdate(deltaTime: Float) {
        _playerPosition.update { player ->
            var newY =
                player.y + (player.speed * deltaTime + 0.5 * Player.acceleration * deltaTime * deltaTime).dp
            var newSpeed = player.speed + Player.acceleration * deltaTime

            if (newY > 2000.dp) {
                newY = 0.dp
                newSpeed = 0f
            }

            player.copy(y = newY, speed = newSpeed)
        }
    }

    fun jump() {
        gameStatusLogic.gameStarted()
        _playerPosition.update {
            it.copy(speed = -0.30f)
        }
    }

    override fun onGameOver() {
        _playerPosition.value = defaultPlayer
    }
}