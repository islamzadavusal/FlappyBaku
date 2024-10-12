package com.islamzada.flappybaku.ui.game.ui

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.islamzada.flappybaku.ui.game.di.GameDI.Companion.rememberDI
import com.islamzada.flappybaku.ui.game.model.Viewport
import com.islamzada.flappybaku.ui.game.ui.components.Background
import com.islamzada.flappybaku.ui.game.ui.components.Block
import com.islamzada.flappybaku.ui.game.ui.components.GameOverUI
import com.islamzada.flappybaku.ui.game.ui.components.GameStartUI
import com.islamzada.flappybaku.ui.game.ui.components.Player
import com.islamzada.flappybaku.ui.game.ui.components.Score

@Composable
fun Game(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {

        val viewPort = remember {
            Viewport(maxWidth, maxHeight)
        }
        val di = rememberDI(viewPort)

        Box(
            Modifier
                .fillMaxSize()
                .clickable(interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null) {
                    di.playerLogic.jump()
                }) {
            Background(di.timeManager)
            Player(Modifier, di.playerLogic)
            Block(di.blockMovementLogic)
            Score(di)
            GameStartUI(di)
            GameOverUI(di)
        }
    }
}


fun Bitmap.resizeTo(maxHeight: Int): Bitmap {
    val sourceWidth: Int = width
    val sourceHeight: Int = height
    val sourceRatio = sourceWidth.toFloat() / sourceHeight.toFloat()
    val targetWidth = (maxHeight.toFloat() * sourceRatio).toInt()
    return Bitmap.createScaledBitmap(this, targetWidth, maxHeight, true)
}

