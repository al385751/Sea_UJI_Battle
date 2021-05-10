package com.example.a2_seaujibattle.additionalClasses

import android.util.Log

class BoardClass(_coordTL: CellDataClass, _boardWidth: Int, _boardHeight: Int, _cellSide: Float) {
    var coordTL = _coordTL
    var boardWidth = _boardWidth
    var boardHeight = _boardHeight
    var cellSide = _cellSide
    var board : MutableList<MutableList<BoardCellClass>> = mutableListOf()

    fun coordInsideBoard(cell: CellDataClass) : Boolean {
        if (cell.x < this.coordTL.x || cell.x >= (this.coordTL.x + this.boardWidth))
            return false
        else return !(cell.y < this.coordTL.y || cell.y >= (this.coordTL.y + this.boardHeight))
    }

    fun whereIsPlaced(boat: ShipClass) : MutableList<BoardCellClass>? {
        val boatPlacement : MutableList<BoardCellClass> = mutableListOf()

        /*val boatNames : ArrayList<String> = arrayListOf("Carrier",
                                                        "BattleshipOne", "BattleshipTwo",
                                                        "ShiprescueOne", "ShiprescueTwo", "ShiprescueThree",
                                                        "ShippatrolOne", "ShippatrolTwo", "ShippatrolThree", "ShippatrolFour")

        // The name of the boat parameter is incorrect
        if (!boatNames.contains(boat.name))
            return null
        */

        for (row in board) {
            for (cell in row) {
                if (cell.hasBoat == boat.name) {
                    boatPlacement.add(cell)
                }
            }
        }
        return boatPlacement
    }

    fun getCellInBoard(cell: CellDataClass) : BoardCellClass? {
        for (row in board) {
            for (cellBoard in row) {
                if (cell.x - 1 == cellBoard.cell.x && cell.y - 2 == cellBoard.cell.y) {
                    return cellBoard
                }
            }
        }
        return null
    }
}