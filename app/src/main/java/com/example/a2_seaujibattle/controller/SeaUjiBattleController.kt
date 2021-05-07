package com.example.a2_seaujibattle.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.a2_seaujibattle.R
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass
import com.example.a2_seaujibattle.additionalClasses.ShipClass
import com.example.a2_seaujibattle.model.Model
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

    val model : Model = Model()

    var draggingShip : Boolean = false
    lateinit var draggedBoat : ShipClass

    init {
        Assets.loadDrawableAssets(applicationContext)
        Assets.createResizedAssets(applicationContext, cellSide.toInt())
    }

    val carrier = ShipClass("Carrier",((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + cellSide) / cellSide).toInt(), Assets.CARRIER_LENGTH, true, false, Assets.horizontalCarrier!!, Assets.verticalCarrier!!, Assets.horizontalCarrierFlames!!, Assets.verticalCarrierFlames!!)
    val battleshipOne = ShipClass("BattleshipOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 3)) / cellSide).toInt(), Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!)
    val battleshipTwo = ShipClass("BattleshipTwo", ((originX2 - xOffset + (cellSide * 5)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 3)) / cellSide).toInt(), Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!)
    val shiprescueOne = ShipClass("ShiprescueOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!)
    val shiprescueTwo = ShipClass("ShiprescueTwo", ((originX2 - xOffset + (cellSide * 4)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!)
    val shiprescueThree = ShipClass("ShiprescueThree", ((originX2 - xOffset + (cellSide * 7)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!)
    val shippatrolOne = ShipClass("ShippatrolOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!)
    val shippatrolTwo = ShipClass("ShippatrolTwo", ((originX2 - xOffset + (cellSide * 3)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!)
    val shippatrolThree = ShipClass("ShippatrolThree", ((originX2 - xOffset + (cellSide * 5)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!)
    val shippatrolFour = ShipClass("ShippatrolFour", ((originX2 - xOffset + (cellSide * 7)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!)

    var shipList : MutableList<ShipClass> = mutableListOf(carrier, battleshipOne, battleshipTwo, shiprescueOne, shiprescueTwo, shiprescueThree, shippatrolOne, shippatrolTwo, shippatrolThree, shippatrolFour)

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if (touchEvents != null) {
            for (event in touchEvents) {
                val correctedEventX : Int = ((event.x - xOffset) / cellSide).toInt()
                val correctedEventY : Int = ((event.y - yOffset) / cellSide).toInt()
                when (event.type) {
                    TouchHandler.TouchType.TOUCH_DOWN -> {
                        if (model.clickedOnBoat(CellDataClass(correctedEventX, correctedEventY), shipList)) {
                            draggingShip = true
                            draggedBoat = model.getClickedBoat(CellDataClass(correctedEventX, correctedEventY), shipList)!!
                        }
                    }

                    TouchHandler.TouchType.TOUCH_DRAGGED -> {
                        // ACTIONS FOR TOUCH_DRAGGED
                        if (draggingShip) {
                            draggedBoat.x = correctedEventX
                            draggedBoat.y = correctedEventY
                        }
                        //Log.e("SEA UJI BATTLE", (model.clickedOnBoat(CellDataClass(correctedEventX, correctedEventY), shipList)).toString())
                    }

                    TouchHandler.TouchType.TOUCH_UP -> {
                        // ACTIONS FOR TOUCH_UP
                        draggingShip = false
                        //Log.e("SEA UJI BATTLE", (playerBoard.coordInsideBoard(CellDataClass(correctedEventX, correctedEventY)).toString()))
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
        //drawBattleButton()
        //drawText("Play game")
        //drawBoard(1)

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
        graphics.drawBitmap(carrier.horizontalBoat, carrier.x * cellSide + xOffset, carrier.y * cellSide + yOffset)
        graphics.drawBitmap(battleshipOne.horizontalBoat, battleshipOne.x * cellSide + xOffset, battleshipOne.y * cellSide + yOffset)
        graphics.drawBitmap(battleshipTwo.horizontalBoat, battleshipTwo.x * cellSide + xOffset, battleshipTwo.y * cellSide + yOffset)
        graphics.drawBitmap(shiprescueOne.horizontalBoat, shiprescueOne.x * cellSide + xOffset, shiprescueOne.y * cellSide + yOffset)
        graphics.drawBitmap(shiprescueTwo.horizontalBoat, shiprescueTwo.x * cellSide + xOffset, shiprescueTwo.y * cellSide + yOffset)
        graphics.drawBitmap(shiprescueThree.horizontalBoat, shiprescueThree.x * cellSide + xOffset, shiprescueThree.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolOne.horizontalBoat, shippatrolOne.x * cellSide + xOffset, shippatrolOne.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolTwo.horizontalBoat, shippatrolTwo.x * cellSide + xOffset, shippatrolTwo.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolThree.horizontalBoat, shippatrolThree.x * cellSide + xOffset, shippatrolThree.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolFour.horizontalBoat, shippatrolFour.x * cellSide + xOffset, shippatrolFour.y * cellSide + yOffset)
    }

    private fun drawBattleButton() {
        graphics.drawRect(originX2 + (cellSide * 5), originY2 + (cellSide * 6), cellSide*4, cellSide * 4, Color.parseColor("#FFBF00"))
        graphics.setTextColor(Color.BLACK)
        graphics.drawText(originX2 + (cellSide * 7), (originY2 + (cellSide * 8.25)).toFloat(), "Battle!")
    }
}