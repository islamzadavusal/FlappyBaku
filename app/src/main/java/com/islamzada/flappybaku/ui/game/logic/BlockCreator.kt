package com.islamzada.flappybaku.ui.game.logic

import androidx.compose.ui.unit.dp
import com.islamzada.flappybaku.ui.game.model.Block
import com.islamzada.flappybaku.ui.game.model.Pipe
import com.islamzada.flappybaku.ui.game.model.Viewport
import java.util.Random

// BlockCreator is responsible for creating obstacles (pipes) with random heights.
class BlockCreator(private val viewport: Viewport) {
    private val minimumHeightPercentage = 0.1f
    private val minimumGateHeightPercentage = 0.3f
    private val maximumGateHeightPercentage = 0.5f

    // createBlock generates new block obstacles with random gaps (gate heights).
    fun createBlock(): Block {
        val totalHeight = viewport.height
        val maximumGateHeight = totalHeight * maximumGateHeightPercentage
        val usableHeight = (totalHeight * (1f - minimumHeightPercentage)).coerceAtMost(maximumGateHeight)
        val minimumHeight = usableHeight * minimumGateHeightPercentage
        val pipeMinimumHeight = viewport.height * (minimumHeightPercentage / 2f)

        val randomFloat = Random().nextFloat()
        val gateHeight = minimumHeight + (usableHeight - minimumHeight) * randomFloat
        val maximumGateEndY = totalHeight * (1f - minimumHeightPercentage) - gateHeight
        val gateStart = pipeMinimumHeight + maximumGateEndY * (Random().nextFloat())

        return Block(
            Pipe(0.dp, gateStart, viewport.width),
            Pipe(gateStart + gateHeight, totalHeight, viewport.width)
        )
    }
}