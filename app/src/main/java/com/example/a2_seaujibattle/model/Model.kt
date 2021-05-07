package com.example.a2_seaujibattle.model

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
}