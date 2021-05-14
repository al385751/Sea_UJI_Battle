package com.example.a2_seaujibattle.computerStrategy

import com.example.a2_seaujibattle.additionalClasses.BoardCellClass
import com.example.a2_seaujibattle.additionalClasses.BoardClass

class SmartStrategy(_playerBoard : BoardClass) :  StrategyInterface {
    private var playerBoard = _playerBoard

    override fun computerGuess(): BoardCellClass {
        TODO("Not yet implemented")
    }

    override fun shotResults(cell: BoardCellClass) {
        TODO("Not yet implemented")
    }
}