package com.example.a2_seaujibattle.controller

import android.content.Context
import android.graphics.Bitmap
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import kotlin.math.min

private const val TOTAL_CELLS_WIDTH  = 24
private const val TOTAL_CELLS_HEIGHT  = 14

class SeaUjiBattleController(width: Int, height: Int, applicationContext: Context) : IGameController {
    private val cellSide : Float = min(width.toFloat() / TOTAL_CELLS_WIDTH,
        height.toFloat() / TOTAL_CELLS_HEIGHT)
    private val xOffset : Float = (width - TOTAL_CELLS_WIDTH * cellSide) / 2.0f
    private val yOffset : Float = (height - TOTAL_CELLS_HEIGHT * cellSide) / 2.0f

    val board : BoardClass = BoardClass(CellDataClass(1, 2),
                                        CellDataClass(11, 2),
                                        CellDataClass(1, 12),
                                        CellDataClass(11, 12))

    val originX : Float = board.coordTL.x * cellSide + xOffset
    val originY : Float = board.coordTL.y * cellSide + yOffset

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        TODO("Not yet implemented")
    }

    override fun onDrawingRequested(): Bitmap {
        TODO("Not yet implemented")
    }
}