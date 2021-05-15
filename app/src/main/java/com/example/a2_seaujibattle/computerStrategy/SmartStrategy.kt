package com.example.a2_seaujibattle.computerStrategy

import android.os.Debug
import android.util.Log
import com.example.a2_seaujibattle.additionalClasses.BoardCellClass
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass
import com.example.a2_seaujibattle.additionalClasses.ShipClass

enum class ComputerModeActSmart {
    HUNT,
    TARGET
}

class SmartStrategy(_playerBoard : BoardClass, _shipList : MutableList<ShipClass>) :  StrategyInterface {
    private var playerBoard = _playerBoard
    private var possibleCells : MutableList<BoardCellClass> = mutableListOf()
    private var notSunkShips : MutableList<ShipClass> = _shipList
    private var canBePlaced : Boolean = true
    private var canHaveBoat : Boolean = false
    private var computerMode : ComputerModeAct = ComputerModeAct.HUNT

    private fun calculateWeights() {
        resetWeights()

        // VERTICAL COSTS
        for (i in 0 until playerBoard.boardWidth) {
            for (j in 0 until playerBoard.boardHeight) {
                if (!playerBoard.board[i][j].isHitted || (playerBoard.board[i][j].isHitted && playerBoard.board[i][j].hasBoat != "")) {
                    for (ship in notSunkShips) {
                        for (k in 0 until ship.shipLength) {
                            if (!playerBoard.coordInsideBoard(CellDataClass(k + i + 1, j + 2)))
                                canBePlaced = false
                            else if (playerBoard.board[i + k][j].isHitted && playerBoard.board[i + k][j].hasBoat == "")
                                canBePlaced = false
                            else if (playerBoard.board[i + k][j].isHitted && playerBoard.board[i + k][j].hasBoat != "")
                                canHaveBoat = true
                        }

                        if (canBePlaced) {
                            for (k in 0 until ship.shipLength) {
                                if (canHaveBoat) {
                                    playerBoard.board[i + k][j].weight += 100
                                }
                                else
                                    playerBoard.board[i + k][j].weight++
                            }
                            canHaveBoat = false
                        }

                        else
                            canBePlaced = true

                        if (playerBoard.board[i][j].isHitted && playerBoard.board[i][j].hasBoat != "")
                            playerBoard.board[i][j].weight = 0
                    }
                }
                else
                    playerBoard.board[i][j].weight = 0
            }
        }

        // HORIZONTAL COSTS
        for (i in 0 until playerBoard.boardHeight) {
            for (j in 0 until playerBoard.boardWidth) {
                if (!playerBoard.board[j][i].isHitted || (playerBoard.board[j][i].isHitted && playerBoard.board[j][i].hasBoat != "")) {
                    for (ship in notSunkShips) {
                        for (k in 0 until ship.shipLength) {
                            if (!playerBoard.coordInsideBoard(CellDataClass(j + 1, i + k + 2)))
                                canBePlaced = false
                            else if (playerBoard.board[j][i + k].isHitted && playerBoard.board[j][i + k].hasBoat == "")
                                canBePlaced = false
                            else if (playerBoard.board[j][i + k].isHitted && playerBoard.board[j][i + k].hasBoat != "")
                                canHaveBoat = true
                        }

                        if (canBePlaced) {
                            for (k in 0 until ship.shipLength) {
                                if (canHaveBoat) {
                                    playerBoard.board[j][i + k].weight += 100
                                }
                                else
                                    playerBoard.board[j][i + k].weight++
                            }
                            canHaveBoat = false
                        }

                        else
                            canBePlaced = true
                    }

                    if (playerBoard.board[j][i].isHitted)
                        playerBoard.board[j][i].weight = 0
                }
                else
                    playerBoard.board[j][i].weight = 0
            }
        }

        var maxWeight = 0
        for (row in playerBoard.board) {
            for (cell in row) {
                if (cell.weight > maxWeight)
                    maxWeight = cell.weight
            }
        }

        possibleCells = mutableListOf()
        for (row in playerBoard.board) {
            for (cell in row) {
                if (cell.weight == maxWeight) {
                    possibleCells.add(cell)
                }
            }
        }
    }

    override fun computerGuess(): BoardCellClass {
        calculateWeights()
        val randomCell = (0 until possibleCells.count()).random()
        return possibleCells[randomCell]
    }

    override fun shotResults(cell: BoardCellClass) {
        if (cell.hasBoat != "") {
            val hittedBoat = playerBoard.getBoatInCell(cell, notSunkShips)
            if (hittedBoat!!.isSunk) {
                val boatPlacement = playerBoard.whereIsPlaced(hittedBoat)
                for (boatCell in boatPlacement!!) {
                    boatCell.hasBoat = ""
                }
                notSunkShips.remove(hittedBoat)
            }
        }
    }

    private fun resetWeights() {
        for (row in playerBoard.board) {
            for (cell in row) {
                cell.weight = 0
            }
        }
    }
}