package com.example.a2_seaujibattle.additionalClasses

import android.graphics.Bitmap
import com.example.a2_seaujibattle.controller.Assets
import es.uji.vj1229.framework.AnimatedBitmap

class ShipClass(_name : String, _x: Int, _y: Int, _shipLength: Int, _isHorizontal: Boolean, _isSunk: Boolean,
    _horizontalBoat: Bitmap, _verticalBoat: Bitmap, _horizontalFlamesBoat: Bitmap, _verticalFlamesBoat: Bitmap,
    _placed : Boolean, _hits : Int) {
    var name = _name
    var x = _x
    var y = _y
    var shipLength = _shipLength
    var isHorizontal = _isHorizontal
    var isSunk = _isSunk
    var horizontalBoat = _horizontalBoat
    var verticalBoat = _verticalBoat
    var horizontalFlamesBoat = _horizontalFlamesBoat
    var verticalFlamesBoat = _verticalFlamesBoat
    var placed = _placed
    var hits = _hits

    var activeBitmap: Bitmap = _horizontalBoat
    var defaultPosition : CellDataClass = CellDataClass(_x, _y)
}