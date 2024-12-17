package com.example.game2024.gameEngine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game2024.remote.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    var state by mutableStateOf(GameState())
    var playerModel by mutableStateOf(PlayerModel())
    val game2048Engine = Game2048Engine()

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            while (state.lastPlayer != playerModel.id) {
                try {
                    state = RetrofitInstance.api.getGameState()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(1000)
            }
        }
    }

    private fun updateGameState() {
        viewModelScope.launch {
            try {
                state = RetrofitInstance.api.updateGameState(state)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun move(direction: GameDirection) {
        if (state.lastPlayer == playerModel.id) return
        when (direction) {
            GameDirection.UP -> game2048Engine.moveTop()
            GameDirection.DOWN -> game2048Engine.moveBottom()
            GameDirection.LEFT -> game2048Engine.moveLeft()
            GameDirection.RIGHT -> game2048Engine.moveRight()
        }
        game2048Engine.board.forEach {
            println(it)
        }
        state = state.copy(board = game2048Engine.board, lastPlayer = playerModel.id)
        updateGameState()
    }

    fun resetGame() {
        game2048Engine.init()
        state = state.copy(board = game2048Engine.board)
    }
}