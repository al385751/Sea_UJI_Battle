package com.example.a2_seaujibattle.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass
import es.uji.vj1229.framework.Graphics
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

    val board : BoardClass = BoardClass(CellDataClass(1, 2), 10, 10, cellSide)

    val originX : Float = board.coordTL.x * cellSide + xOffset
    val originY : Float = board.coordTL.y * cellSide + yOffset

    val graphics : Graphics = Graphics(width, height)

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if (touchEvents != null) {
            for (event in touchEvents) {
                val correctedEventX : Int = ((event.x - xOffset) / cellSide).toInt()
                val correctedEventY : Int = ((event.y - yOffset) / cellSide).toInt()

                when (event.type) {
                    TouchHandler.TouchType.TOUCH_UP -> {
                        // ACTIONS FOR TOUCH_UP
                    }
                    TouchHandler.TouchType.TOUCH_DRAGGED -> {
                        // ACTIONS FOR TOUCH_DRAGGED
                    }
                }
            }
        }

        // ACTIONS WHICH NOT DEPEND ON USER'S TOUCHES (UPDATES)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(Color.WHITE)

        return graphics.frameBuffer
    }
}