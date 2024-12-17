package com.example.game2024.gameEngine

import java.util.UUID

data class PlayerModel (
    val id: String = UUID.randomUUID().toString()
)
