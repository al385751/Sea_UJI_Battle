package com.example.a2_seaujibattle.computerStrategy

import com.example.a2_seaujibattle.additionalClasses.BoardCellClass

interface StrategyInterface {
    fun computerGuess() : BoardCellClass
    fun shotResults(cell : BoardCellClass)
}