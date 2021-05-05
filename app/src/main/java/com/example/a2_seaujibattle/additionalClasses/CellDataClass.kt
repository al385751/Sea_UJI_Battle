package com.example.a2_seaujibattle.additionalClasses

class CellDataClass constructor(_x: Int, _y: Int) {
    var x = _x
    var y = _y
    var hasBoat : String? = null

    constructor(_x: Int, _y: Int, _hasBoat: String) : this(_x, _y) {
        var x = _x
        var y = _y
        var hasBoat = _hasBoat
    }
}