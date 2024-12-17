package com.example.game2024.remote

import com.example.game2024.remote.service.GameStateService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://7c54-193-170-158-244.ngrok-free.app/"

    val api: GameStateService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GameStateService::class.java)
    }
}