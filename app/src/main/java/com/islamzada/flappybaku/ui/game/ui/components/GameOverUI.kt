package com.islamzada.flappybaku.ui.game.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islamzada.flappybaku.ui.game.di.GameDI
import com.islamzada.flappybaku.ui.game.model.GameStatus

@Composable
internal fun BoxScope.GameOverUI(di: GameDI) {
    val notStarted = di.gameStatusLogic.gameState.collectAsState().value == GameStatus.GameOver
    if (!notStarted) return

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Bəxtinizi bir daha sınayın :)",
            color = Color.Red,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .offset(y = (-140).dp)
                .padding(16.dp),
            fontSize = 45.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            softWrap = true,
            maxLines = 3,
            lineHeight = 45.sp
        )
    }
}
