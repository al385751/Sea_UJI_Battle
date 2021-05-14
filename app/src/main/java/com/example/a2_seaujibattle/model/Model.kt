package com.example.a2_seaujibattle.model

import android.media.SoundPool
import com.example.a2_seaujibattle.additionalClasses.*

enum class SeaBattleAction {
    PLACE_SHIPS,
    PLAYER_TURN,
    COMPUTER_TURN,
    WAITING_WATER,
    WAITING_EXPLOSION,
    END_WIN,
    END_LOSE
}

interface SoundPlayer {
    fun playSelectedSound(sound : Int)
}

class Model(pool: SoundPool) : SoundPlayer {
    var state = SeaBattleAction.PLACE_SHIPS
        private set
    var soundPool : SoundPool = pool

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
        if (ship.isHorizontal) {
            if (ship.shipLength == 4 && ship.x > 20) {
                ship.x = 20
            }

            else if (ship.shipLength == 3 && ship.x > 21) {
                ship.x = 21
            }

            else if (ship.shipLength == 2 && ship.x > 22) {
                ship.x = 22
            }

            else if (ship.shipLength == 1 && ship.x > 23) {
                ship.x = 23
            }

            if (ship.y > 13) {
                ship.y = 13
            }
        }

        else {
            if (ship.shipLength == 4 && ship.y > 10) {
                ship.y = 10
            }

            else if (ship.shipLength == 3 && ship.y > 11) {
                ship.y = 11
            }

            else if (ship.shipLength == 2 && ship.y > 12) {
                ship.y = 12
            }

            else if (ship.shipLength == 1 && ship.y > 13) {
                ship.y = 13
            }

            if (ship.x > 23) {
                ship.x = 23
            }
        }
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

    fun resetPosition(ship: ShipClass, board: BoardClass) {
        if (!board.coordInsideBoard(ship.defaultPosition))
            ship.placed = false
        ship.x = ship.defaultPosition.x
        ship.y = ship.defaultPosition.y
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
        ship.defaultPosition.x = ship.x
        ship.defaultPosition.y = ship.y
        if (ship.isHorizontal) {
            for (i in 0 until ship.shipLength) {
                val overCell = board.getCellInFirstBoard(CellDataClass(ship.x + i, ship.y))
                overCell!!.hasBoat = ship.name
                overCell.partOfBoat = i + 1
            }
            ship.placed = true
        }

        else {
            for (i in 0 until ship.shipLength) {
                val overCell = board.getCellInFirstBoard(CellDataClass(ship.x, ship.y + i))
                overCell!!.hasBoat = ship.name
                overCell.partOfBoat = i + 1
            }
            ship.placed = true
        }
    }

    fun resetBoardOfBoat(ship: ShipClass, board: BoardClass) {
        for (row in board.board) {
            for (cell in row) {
                if (cell.hasBoat == ship.name) {
                    cell.hasBoat = ""
                    cell.partOfBoat = -1
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

    fun generateNewBoard(board: BoardClass, shipList : MutableList<ShipClass>) {
        state = SeaBattleAction.PLAYER_TURN
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
                    cell.partOfBoat = i + 1
                }
            }

            else {
                for (i in 0 until boat.shipLength) {
                    val cell : BoardCellClass? = board.getCellInSecondBoard(CellDataClass(boat.x, boat.y + i))
                    cell!!.hasBoat = boat.name
                    cell.partOfBoat = i + 1
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

    fun changeAnimationPosition(animation: Animation, cell: CellDataClass, newState: SeaBattleAction) {
        state = newState
        animation.x = cell.x
        animation.y = cell.y
    }

    fun changeGameState(gameState: SeaBattleAction) {
        state = gameState
    }

    override fun playSelectedSound(sound: Int) {
        soundPool.play(sound, 1F, 1F, 1, 0, 1F)
    }

    fun getRandomCellInBoard(board: BoardClass) : BoardCellClass {
        var coordX : Int = 0
        var coordY : Int = 0
        var selected = false
        while (!selected) {
            coordX = (board.coordTL.x until board.coordTL.x + board.boardWidth).random()
            coordY = (board.coordTL.y until board.coordTL.y + board.boardHeight).random()
            if (!board.getCellInFirstBoard(CellDataClass(coordX, coordY))!!.isHitted)
                selected = true
        }
        return board.getCellInFirstBoard(CellDataClass(coordX, coordY))!!
    }

    fun getAllBoatsSunk(ships: MutableList<ShipClass>): Boolean {
        var allSunk : Boolean = true
        for (ship in ships) {
            if (!ship.isSunk)
                allSunk = false
        }
        return allSunk
    }

    fun checkIfRestartPressed(cell: CellDataClass): Boolean {
        return (cell.x in 9..14) && (cell.y in 12..13)
    }
}