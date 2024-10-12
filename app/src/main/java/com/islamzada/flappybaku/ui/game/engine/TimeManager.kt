package com.islamzada.flappybaku.ui.game.engine

import androidx.compose.runtime.withFrameMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

// TimeManager manages the timing of the game, calculating frame time for smooth animation.
class TimeManager(coroutineScope: CoroutineScope) {
    // deltaTime calculates the time difference between frames.
    val deltaTime by lazy {
        flow {
            var lastFrame: Long? = null
            while (true) {
                var frame = 0.0f
                withFrameMillis {
                    if (lastFrame != null) {
                        frame = (it - lastFrame!!).toFloat()
                    }
                    lastFrame = it
                }
                if (frame != 0.0f) {
                    emit(frame)
                }
            }
        }.shareIn(coroutineScope, SharingStarted.Eagerly)
    }
}