package com.example.a2_seaujibattle.model

import android.util.Log
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass
import com.example.a2_seaujibattle.additionalClasses.ShipClass

class Model {
    fun clickedOnBoat(coord : CellDataClass, shipList : MutableList<ShipClass>) : Boolean {
        for (ship in shipList) {
            if (ship.isHorizontal) {
                for (i in 0 until ship.shipLength) {
                    if (ship.x + i == coord.x && ship.y == coord.y)
                        return true
                }
            }

            else {
                for (i in 0 until ship.shipLength) {
                    if (ship.x == coord.x && ship.y + i == coord.y)
                        return true
                }
            }
        }
        return false
    }

    fun getClickedBoat(coord : CellDataClass, shipList : MutableList<ShipClass>) : ShipClass? {
        for (ship in shipList) {
            if (ship.isHorizontal) {
                for (i in 0 until ship.shipLength) {
                    if (ship.x + i == coord.x && ship.y == coord.y)
                        return ship
                }
            }

            else {
                for (i in 0 until ship.shipLength) {
                    if (ship.x == coord.x && ship.y + i == coord.y)
                        return ship
                }
            }
        }
        return null
    }

    fun moveBoat(coord: CellDataClass, ship: ShipClass) {
        ship.x = coord.x
        ship.y = coord.y
    }

    fun rotateBoat(ship : ShipClass) {
        if (ship.isHorizontal) {
            ship.activeBitmap = ship.verticalBoat
            ship.isHorizontal = false
        }
        else {
            ship.activeBitmap = ship.horizontalBoat
            ship.isHorizontal = true
        }
    }

    fun checkPosition(cell: CellDataClass, draggedBoat: ShipClass, board: BoardClass) : Boolean {
        if (draggedBoat.isHorizontal) {
            for (i in 0 until draggedBoat.shipLength) {
                if (board.coordInsideBoard(CellDataClass(draggedBoat.x + i, draggedBoat.y))) {
                    val overCell = board.getCellInFirstBoard(CellDataClass(draggedBoat.x + i, draggedBoat.y))
                    overCell!!.isCovered = true
                }
                else return false
            }
            return true
        }
        else {
            for (i in 0 until draggedBoat.shipLength) {
                if (board.coordInsideBoard(CellDataClass(draggedBoat.x, draggedBoat.y + i))) {
                    val overCell = board.getCellInFirstBoard(CellDataClass(draggedBoat.x, draggedBoat.y + i))
                    overCell!!.isCovered = true
                }
                else return false
            }
            return true
        }
    }

    fun changeWaterCells(board: BoardClass) {
        for (row in board.board) {
            for (cell in row) {
                if (cell.isCovered)
                    cell.activeBitmap = cell.redWater
                else
                    cell.activeBitmap = cell.normalWater
            }
        }
    }

    fun resetWaterCells(board: BoardClass) {
        for (row in board.board) {
            for (cell in row) {
                cell.isCovered = false
            }
        }
    }

    fun resetPosition(ship: ShipClass, defaultShips: MutableList<CellDataClass>) {
        ship.placed = false
        if (ship.name == "Carrier") {
            ship.x = defaultShips[0].x
            ship.y = defaultShips[0].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "BattleshipOne") {
            ship.x = defaultShips[1].x
            ship.y = defaultShips[1].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "BattleshipTwo") {
            ship.x = defaultShips[2].x
            ship.y = defaultShips[2].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "ShiprescueOne") {
            ship.x = defaultShips[3].x
            ship.y = defaultShips[3].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "ShiprescueTwo") {
            ship.x = defaultShips[4].x
            ship.y = defaultShips[4].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "ShiprescueThree") {
            ship.x = defaultShips[5].x
            ship.y = defaultShips[5].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "ShippatrolOne") {
            ship.x = defaultShips[6].x
            ship.y = defaultShips[6].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "ShippatrolTwo") {
            ship.x = defaultShips[7].x
            ship.y = defaultShips[7].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "ShippatrolThree") {
            ship.x = defaultShips[8].x
            ship.y = defaultShips[8].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }

        else if (ship.name == "ShippatrolFour") {
            ship.x = defaultShips[9].x
            ship.y = defaultShips[9].y
            ship.isHorizontal = true
            ship.activeBitmap = ship.horizontalBoat
        }
    }

    fun checkRotation(ship: ShipClass, board: BoardClass): Boolean {
        if (ship.isHorizontal) {
            for (i in 1 until ship.shipLength) {
                if (board.coordInsideBoard(CellDataClass(ship.x, ship.y + i))) {
                    val overCell = board.getCellInFirstBoard(CellDataClass(ship.x, ship.y + i))
                    if (overCell!!.hasBoat != "" && overCell.hasBoat != ship.name) {
                        return false
                    }
                }
                else return false
            }
            return true
        }
        else {
            for (i in 1 until ship.shipLength) {
                if (board.coordInsideBoard(CellDataClass(ship.x + i, ship.y))) {
                    val overCell = board.getCellInFirstBoard(CellDataClass(ship.x + i, ship.y))
                    if (overCell!!.hasBoat != "" && overCell.hasBoat != ship.name) {
                        return false
                    }
                }
                else return false
            }
            return true
        }
    }

    fun colocateShip(ship: ShipClass, board: BoardClass) {
        if (ship.isHorizontal) {
            for (i in 0 until ship.shipLength) {
                val overCell = board.getCellInFirstBoard(CellDataClass(ship.x + i, ship.y))
                overCell!!.hasBoat = ship.name
            }
            ship.placed = true
        }

        else {
            for (i in 0 until ship.shipLength) {
                val overCell = board.getCellInFirstBoard(CellDataClass(ship.x, ship.y + i))
                overCell!!.hasBoat = ship.name
            }
            ship.placed = true
        }
    }

    fun resetBoardOfBoat(ship: ShipClass, board: BoardClass) {
        for (row in board.board) {
            for (cell in row) {
                if (cell.hasBoat == ship.name) {
                    cell.hasBoat = ""
                }
            }
        }
    }

    fun checkIfThereIsBoat(ship: ShipClass, board: BoardClass) : Boolean {
        if (ship.isHorizontal) {
            for (i in 0 until ship.shipLength) {
                if (board.coordInsideBoard(CellDataClass(ship.x + i, ship.y))) {
                    val overCell = board.getCellInFirstBoard(CellDataClass(ship.x + i, ship.y))
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
                    val overCell = board.getCellInFirstBoard(CellDataClass(ship.x, ship.y + i))
                    if (overCell!!.hasBoat != "" && overCell.hasBoat != ship.name) {
                        return true
                    }
                }
                else return true
            }
            return false
        }
    }

    fun checkBoatsPlaced(shipList: MutableList<ShipClass>) : Boolean {
        var draw = true
        for (ship in shipList) {
            if (!ship.placed)
                draw = false
        }
        return draw
    }

    fun checkIfButtonPressed(cell: CellDataClass): Boolean {
        val buttonCells : List<CellDataClass> = listOf(CellDataClass(18, 8), CellDataClass(19, 8), CellDataClass(20, 8), CellDataClass(21, 8),
                                                        CellDataClass(18, 9), CellDataClass(19, 9), CellDataClass(20, 9), CellDataClass(21, 9),
                                                        CellDataClass(18, 10), CellDataClass(19, 10), CellDataClass(20, 10), CellDataClass(21, 10),
                                                        CellDataClass(18, 11), CellDataClass(19, 11), CellDataClass(20, 11), CellDataClass(21, 11))
        for (buttonCell in buttonCells) {
            if (buttonCell.x == cell.x && buttonCell.y == cell.y)
                return true
        }
        return false
    }
}