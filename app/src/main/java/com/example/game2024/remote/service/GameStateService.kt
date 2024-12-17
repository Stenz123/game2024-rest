package com.example.game2024.remote.service

import com.example.game2024.gameEngine.GameState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface GameStateService {
    @GET("gameState")
    suspend fun getGameState(): GameState

    @POST("gameState")
    suspend fun setGameState(@Body gameState: GameState): GameState

    @PUT("gameState")
    suspend fun updateGameState(@Body gameState: GameState): GameState
}