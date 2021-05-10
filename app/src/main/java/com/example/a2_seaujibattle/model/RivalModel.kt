package com.example.a2_seaujibattle.model

import android.util.Log
import com.example.a2_seaujibattle.additionalClasses.BoardCellClass
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass
import com.example.a2_seaujibattle.additionalClasses.ShipClass
import kotlin.math.log

class RivalModel {
    fun generateNewBoard(board: BoardClass, shipList : MutableList<ShipClass>) {
        for (boat in shipList) {
            val random = (0 until 2).random()
            boat.isHorizontal = random == 0
            if (!boat.isHorizontal)
                boat.activeBitmap = boat.verticalBoat
        }

        for (boat in shipList) {
            var placed = false
            while (!placed) {
                boat.x = board.coordTL.x + (0 until board.boardWidth).random()
                boat.y = board.coordTL.y + (0 until board.boardHeight).random()
                if (boat.isHorizontal) {
                    placed = true
                    if (!checkIfThereIsBoatInSecondBoard(boat, board)) {
                        for (i in 0 until boat.shipLength) {
                            if (!board.coordInsideBoard(CellDataClass(boat.x + i, boat.y))) {
                                placed = false
                            }
                        }
                    }
                    else placed = false
                }

                else {
                    placed = true
                    if (!checkIfThereIsBoatInSecondBoard(boat, board)) {
                        for (i in 0 until boat.shipLength) {
                            if (!board.coordInsideBoard(CellDataClass(boat.x, boat.y + i))) {
                                placed = false
                            }
                        }
                    }
                    else placed = false
                }
            }

            if (boat.isHorizontal) {
                for (i in 0 until boat.shipLength) {
                    val cell : BoardCellClass? = board.getCellInSecondBoard(CellDataClass(boat.x + i, boat.y))
                    cell!!.hasBoat = boat.name
                }
            }

            else {
                for (i in 0 until boat.shipLength) {
                    val cell : BoardCellClass? = board.getCellInSecondBoard(CellDataClass(boat.x, boat.y + i))
                    cell!!.hasBoat = boat.name
                }
            }
        }
    }

    fun checkIfThereIsBoatInSecondBoard(ship: ShipClass, board: BoardClass) : Boolean {
        if (ship.isHorizontal) {
            for (i in 0 until ship.shipLength) {
                if (board.coordInsideBoard(CellDataClass(ship.x + i, ship.y))) {
                    val overCell = board.getCellInSecondBoard(CellDataClass(ship.x + i, ship.y))
                    if (overCell!!.hasBoat != "" && overCell.hasBoat != ship.name) {
                        return true
                    }
                }
                else return true
            }
            return false
        }
        else {
            for (i in 0 until ship.shipLength) {
                if (board.coordInsideBoard(CellDataClass(ship.x, ship.y + i))) {
                    val overCell = board.getCellInSecondBoard(CellDataClass(ship.x, ship.y + i))
                    if (overCell!!.hasBoat != "" && overCell.hasBoat != ship.name) {
                        return true
                    }
                }
                else return true
            }
            return false
        }
    }
}