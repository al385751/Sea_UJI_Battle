package com.example.a2_seaujibattle.controller

import android.content.Context
import android.graphics.Bitmap
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class SeaUjiBattleController(width: Int, height: Int, applicationContext: Context) : IGameController {
    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        TODO("Not yet implemented")
    }

    override fun onDrawingRequested(): Bitmap {
        TODO("Not yet implemented")
    }
}