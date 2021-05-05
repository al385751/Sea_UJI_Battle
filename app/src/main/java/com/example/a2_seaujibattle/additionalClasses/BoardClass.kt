package com.example.a2_seaujibattle.additionalClasses

class BoardClass(_coordTL: CellDataClass, _boardWidth: Int, _boardHeight: Int, _cellSide: Float) {
    var coordTL = _coordTL
    var boardWidth = _boardWidth
    var boardHeight = _boardHeight
    var cellSide = _cellSide

    fun coordInsideBoard(cell: CellDataClass) : Boolean {
        if (cell.x < this.coordTL.x || cell.x >= (this.coordTL.x + this.boardWidth))
            return false
        else return !(cell.y < this.coordTL.y || cell.y >= (this.coordTL.y + this.boardHeight))
    }

    fun whereIsPlaced(boat: String) : MutableList<CellDataClass>? {
        val boatPlacement : MutableList<CellDataClass> = ArrayList()
        val boatNames : ArrayList<String> = arrayListOf("Boat 4.1",
                                                        "Boat 3.1", "Boat 3.2",
                                                        "Boat 2.1", "Boat 2.2", "Boat 2.3",
                                                        "Boat 1.1", "Boat 1.2", "Boat 1.3", "Boat 1.4")

        // The name of the boat parameter is incorrect
        if (!boatNames.contains(boat))
            return null

        TODO("Check every cell finding the boat requested")

        return boatPlacement
    }
}