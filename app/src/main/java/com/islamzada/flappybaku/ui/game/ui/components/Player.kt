package com.islamzada.flappybaku.ui.game.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.islamzada.flappybaku.R
import com.islamzada.flappybaku.ui.game.logic.PlayerLogic
import com.islamzada.flappybaku.ui.game.model.toDpSize

@Composable
internal fun Player(
    modifier: Modifier,
    playerLogic: PlayerLogic,
) {
    Box(modifier) {

        val player = playerLogic.player.collectAsState()
        Image(
            painterResource(id = R.drawable.car),
            contentDescription = null,
            Modifier
                .offset {
                    IntOffset(x = 0, y = player.value.y.roundToPx())
                }
                .size(player.value.size.toDpSize() * 2)
                .graphicsLayer {
                    rotationZ = (player.value.speed * 90f).coerceIn(-60f, 60f)
                }
        )
    }
}