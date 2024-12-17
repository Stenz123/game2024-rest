package com.example.game2024.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.example.game2024.gameEngine.GameDirection
import com.example.game2024.gameEngine.GameViewModel
import com.example.game2024.gameEngine.PlayerModel
import kotlin.math.abs

@Composable
fun UiBoard(viewModel: GameViewModel, modifier: Modifier = Modifier) {
    val background = if (!viewModel.isActivePlayer()) {
       Color.Red
    }else {
        Color.White
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { _ ->
                        // Reset last drag offset when drag starts
                        lastDragOffset = Offset.Zero
                    },
                    onDrag = { change, dragAmount ->
                        change.consume() // Consume the gesture
                        lastDragOffset += dragAmount
                    },
                    onDragEnd = {
                        // Determine the drag direction
                        val direction = detectDragDirection(lastDragOffset)
                        direction?.let {
                            viewModel.move(it)
                        }
                    }
                )
            }
            .background(background)
    ) {
        Column {
            val state = viewModel.state
            state.board.forEach { row ->
                Row {
                    row.forEach { cell ->
                        UiCell(
                            symbol = cell.toString(),
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

private var lastDragOffset = Offset.Zero

fun detectDragDirection(dragOffset: Offset): GameDirection? {
    val threshold = 50f // Minimum movement threshold for a valid swipe
    val (dx, dy) = dragOffset

    return when {
        abs(dy) > abs(dx) && abs(dy) > threshold -> {
            if (dy < 0) GameDirection.UP else GameDirection.DOWN
        }
        abs(dx) > abs(dy) && abs(dx) > threshold -> {
            if (dx < 0) GameDirection.LEFT else GameDirection.RIGHT
        }
        else -> null // Ignore small movements
    }
}
