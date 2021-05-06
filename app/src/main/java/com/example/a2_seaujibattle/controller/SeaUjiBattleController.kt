package com.example.a2_seaujibattle.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import com.example.a2_seaujibattle.R
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

    val playerBoard : BoardClass = BoardClass(CellDataClass(1, 2), 10, 10, cellSide)
    val rivalBoard : BoardClass = BoardClass(CellDataClass(13, 2), 10, 10, cellSide)

    val originX : Float = playerBoard.coordTL.x * cellSide + xOffset
    val originY : Float = playerBoard.coordTL.y * cellSide + yOffset

    val originX2 : Float = rivalBoard.coordTL.x * cellSide + xOffset
    val originY2 : Float = rivalBoard.coordTL.y * cellSide + yOffset

    val graphics : Graphics = Graphics(width, height)
    val myTypeface = ResourcesCompat.getFont(applicationContext, R.font.amarante)

    init {
        Assets.loadDrawableAssets(applicationContext)
        Assets.createResizedAssets(applicationContext, cellSide.toInt())
    }

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

        drawBoard(0)
        drawText("Place ships")
        drawBoats()

        return graphics.frameBuffer
    }

    private fun drawBoard(player : Int) {
        if (player == 0) {
            for (row in 0 until playerBoard.boardHeight) {
                for (col in 0 until playerBoard.boardWidth) {
                    graphics.drawBitmap(Assets.waterUntouched, originX + (cellSide * row), originY + (cellSide * col))
                }
            }
        }
        else if (player == 1) {
            for (row in 0 until rivalBoard.boardHeight) {
                for (col in 0 until rivalBoard.boardWidth) {
                    graphics.drawBitmap(Assets.waterUntouched, originX2 + (cellSide * row), originY2 + (cellSide * col))
                }
            }
        }
    }

    private fun drawText(state : String) {
        graphics.setTextSize(90)
        graphics.setTextAlign(Paint.Align.CENTER)
        graphics.setTextColor(Color.parseColor("#FFBF00"))
        graphics.setTypeface(myTypeface)

        if (state == "Place ships"){
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Time to place your ships!!")
        }

        else if (state == "Play game") {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Sink the enemy's navy")
        }

        else if (state == "Win") {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Congrats!! You won the game!")
        }

        else if (state == "Lose") {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Oh no!! You lost!")
        }
    }

    private fun drawBoats() {
        graphics.drawBitmap(Assets.horizontalCarrier, originX2 + cellSide, originY2 + cellSide)
        graphics.drawBitmap(Assets.horizontalBattleship, originX2 + cellSide, originY2 + (cellSide * 3))
        graphics.drawBitmap(Assets.horizontalBattleship, originX2 + (cellSide * 5), originY2 + (cellSide * 3))
        graphics.drawBitmap(Assets.horizontalShipRescue, originX2 + cellSide, originY2 + (cellSide * 5))
        graphics.drawBitmap(Assets.horizontalShipRescue, originX2 + (cellSide * 4), originY2 + (cellSide * 5))
        graphics.drawBitmap(Assets.horizontalShipRescue, originX2 + (cellSide * 7), originY2 + (cellSide * 5))
        graphics.drawBitmap(Assets.horizontalShipPatrol, originX2 + cellSide, originY2 + (cellSide * 7))
        graphics.drawBitmap(Assets.horizontalShipPatrol, originX2 + (cellSide * 3), originY2 + (cellSide * 7))
        graphics.drawBitmap(Assets.horizontalShipPatrol, originX2 + (cellSide * 5), originY2 + (cellSide * 7))
        graphics.drawBitmap(Assets.horizontalShipPatrol, originX2 + (cellSide * 7), originY2 + (cellSide * 7))
    }
}