package com.example.game2024.gameEngine

import android.provider.Settings
import java.security.AccessController.getContext
import java.util.UUID

data class GameState(
    val board: List<List<Int>> = listOf(
        listOf(0, 0, 0, 0),
        listOf(0, 0, 0, 0),
        listOf(4, 4, 4, 4),
        listOf(0, 0, 0, 0)
    ),

    val lastPlayer: String = ""
)
