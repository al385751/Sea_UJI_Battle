package com.example.a2_seaujibattle.computerStrategy

import android.util.Log
import com.example.a2_seaujibattle.additionalClasses.BoardCellClass
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass

enum class ComputerModeAct {
    HUNT,
    TARGET
}

class SimpleStrategy(_playerBoard : BoardClass) : StrategyInterface {
    private var playerBoard = _playerBoard
    private var possibleCells : MutableList<BoardCellClass> = mutableListOf()
    private var computerMode : ComputerModeAct = ComputerModeAct.HUNT

    override fun computerGuess(): BoardCellClass {
        if (possibleCells.isEmpty())
            computerMode = ComputerModeAct.HUNT

        if (computerMode == ComputerModeAct.HUNT) {
            var coordX = 0
            var coordY = 0
            var selected = false
            while (!selected) {
                coordX = (playerBoard.coordTL.x until playerBoard.coordTL.x + playerBoard.boardWidth).random()
                coordY = (playerBoard.coordTL.y until playerBoard.coordTL.y + playerBoard.boardHeight).random()
                if (!playerBoard.getCellInFirstBoard(CellDataClass(coordX, coordY))!!.isHitted)
                    selected = true
            }
            return playerBoard.getCellInFirstBoard(CellDataClass(coordX, coordY))!!
        }

        else {
            val selectedCell = possibleCells[0]
            possibleCells.removeAt(0)
            return selectedCell
        }
    }

    override fun shotResults(cell : BoardCellClass) {
        if (cell.hasBoat != "") {
            computerMode = ComputerModeAct.TARGET
            if (playerBoard.coordInsideBoard(CellDataClass(cell.cell.x, cell.cell.y + 2)) && !playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x, cell.cell.y + 2))!!.isHitted) {
                possibleCells.add(playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x , cell.cell.y + 2))!!)
            }

            if (playerBoard.coordInsideBoard(CellDataClass(cell.cell.x + 2, cell.cell.y + 2)) && !playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x + 2, cell.cell.y + 2))!!.isHitted) {
                possibleCells.add(playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x + 2, cell.cell.y + 2))!!)
            }

            if (playerBoard.coordInsideBoard(CellDataClass(cell.cell.x + 1, cell.cell.y + 1)) && !playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x + 1, cell.cell.y + 1))!!.isHitted) {
                possibleCells.add(playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x + 1, cell.cell.y + 1))!!)
            }

            if (playerBoard.coordInsideBoard(CellDataClass(cell.cell.x + 1, cell.cell.y + 3)) && !playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x + 1, cell.cell.y + 3))!!.isHitted) {
                possibleCells.add(playerBoard.getCellInFirstBoard(CellDataClass(cell.cell.x + 1, cell.cell.y + 3))!!)
            }
        }
    }

}