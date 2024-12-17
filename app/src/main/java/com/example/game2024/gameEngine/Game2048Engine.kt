package com.example.game2024.gameEngine

import java.util.Collections
import java.util.LinkedList
import java.util.Random

class Game2048Engine {

    var board: MutableList<MutableList<Int>> = mutableListOf()
    private val random = Random()

    fun init() {
        board = LinkedList()
        for (i in 0..3) {
            board.add(LinkedList(Collections.nCopies(4, 0)))
        }

        spawnRandomTile()
        spawnRandomTile()
    }

    fun moveTop() {
        transposeBoard()
        var moved = false
        for (row in board) {
            if (shiftAndMergeRow(row)) {
                moved = true
            }
        }
        transposeBoard()
        if (moved) {
            spawnRandomTile()
        }
    }

    fun moveBottom() {
        transposeBoard()
        var moved = false
        for (row in board) {
            Collections.reverse(row)
            if (shiftAndMergeRow(row)) {
                moved = true
            }
            Collections.reverse(row)
        }
        transposeBoard()
        if (moved) {
            spawnRandomTile()
        }
    }

    fun moveLeft() {
        var moved = false
        val newBoard: MutableList<MutableList<Int>> = LinkedList()
        for (row in board) {
            val newRow: MutableList<Int> = LinkedList(row)
            if (shiftAndMergeRow(newRow)) {
                moved = true
            }
            newBoard.add(newRow)
        }
        if (moved) {
            board = newBoard
            spawnRandomTile()
        }
    }

    fun moveRight() {
        var moved = false
        val newBoard: MutableList<MutableList<Int>> = LinkedList()
        for (row in board) {
            val reversedRow: MutableList<Int> = LinkedList(row)
            reversedRow.reverse()
            if (shiftAndMergeRow(reversedRow)) {
                moved = true
            }
            reversedRow.reverse()
            newBoard.add(reversedRow)
        }
        if (moved) {
            board = newBoard
            spawnRandomTile()
        }
    }


    // Helper Methods
    private fun shiftAndMergeRow(row: MutableList<Int>): Boolean {
        var moved = false
        // Remove zeros
        val newRow: MutableList<Int> = LinkedList()
        for (num in row) {
            if (num != 0) {
                newRow.add(num)
            }
        }

        // Merge adjacent equal elements
        var i = 0
        while (i < newRow.size - 1) {
            if (newRow[i] == newRow[i + 1]) {
                newRow[i] = newRow[i] * 2
                newRow[i + 1] = 0
                i++ // Skip next element
                moved = true
            }
            i++
        }

        // Remove zeros again
        val finalRow: MutableList<Int> = LinkedList()
        for (num in newRow) {
            if (num != 0) {
                finalRow.add(num)
            }
        }

        // Pad with zeros to maintain row size
        while (finalRow.size < 4) {
            finalRow.add(0)
        }

        // Check if the row has changed
        if (row != finalRow) {
            moved = true
            // Update the original row
            for (i in 0..3) {
                row[i] = finalRow[i]
            }
        }

        return moved
    }

    private fun transposeBoard() {
        val transposed: MutableList<MutableList<Int>> = LinkedList()
        for (i in 0..3) {
            val newRow: MutableList<Int> = LinkedList()
            for (j in 0..3) {
                newRow.add(board[j][i])
            }
            transposed.add(newRow)
        }
        board = transposed
    }

    private fun spawnRandomTile() {
        // Find empty positions
        val emptyPositions: MutableList<IntArray> = LinkedList()
        for (i in 0..3) {
            for (j in 0..3) {
                if (board[i][j] == 0) {
                    emptyPositions.add(intArrayOf(i, j))
                }
            }
        }
        if (emptyPositions.isNotEmpty()) {
            // Randomly select an empty position
            val pos = emptyPositions[random.nextInt(emptyPositions.size)]
            // Decide whether to place 2 or 4
            val value = if (random.nextDouble() < 0.9) 2 else 4
            board[pos[0]][pos[1]] = value
        }
    }
}