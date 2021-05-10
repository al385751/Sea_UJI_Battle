package com.example.a2_seaujibattle.additionalClasses

import android.graphics.Bitmap

class BoardCellClass(_cell: CellDataClass, _normalWater : Bitmap, _redWater : Bitmap, _hittedWater : Bitmap,
                     _isCovered : Boolean, _isHitted : Boolean) {
    var cell = _cell
    var normalWater = _normalWater
    var redWater = _redWater
    var hittedWater = _hittedWater
    var isCovered = _isCovered
    var isHitted = _isHitted

    var activeBitmap : Bitmap = _normalWater
    var hasBoat : String = ""
}