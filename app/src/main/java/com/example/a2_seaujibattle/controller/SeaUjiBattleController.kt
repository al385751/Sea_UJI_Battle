package com.example.a2_seaujibattle.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.util.DebugUtils
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.a2_seaujibattle.R
import com.example.a2_seaujibattle.additionalClasses.BoardCellClass
import com.example.a2_seaujibattle.additionalClasses.BoardClass
import com.example.a2_seaujibattle.additionalClasses.CellDataClass
import com.example.a2_seaujibattle.additionalClasses.ShipClass
import com.example.a2_seaujibattle.model.Model
import com.example.a2_seaujibattle.model.RivalModel
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

    val drawnPlayerBoard : MutableList<MutableList<BoardCellClass>> = mutableListOf()
    val drawnRivalBoard : MutableList<MutableList<BoardCellClass>> = mutableListOf()

    val model : Model = Model()
    val rivalModel : RivalModel = RivalModel()

    var draggingShip : Boolean = false
    var clickedShip : Boolean = false
    var draggedBoat : ShipClass? = null

    var gameState : String = "Place ships"
    var drawButton : Boolean = false

    var player : Boolean = true
    var rival : Boolean = false

    var placingShips : Boolean = true
    var playingGame : Boolean = false

    init {
        Assets.loadDrawableAssets(applicationContext)
        Assets.createResizedAssets(applicationContext, cellSide.toInt())

        var columnBoard : MutableList<BoardCellClass>
        for (row in 0 until playerBoard.boardHeight) {
            columnBoard = mutableListOf()
            for (col in 0 until playerBoard.boardWidth) {
                val actualCellBoard = BoardCellClass(CellDataClass(row, col), Assets.waterUntouched!!, Assets.waterOver!!, Assets.waterTouched!!, false, false)
                columnBoard.add(actualCellBoard)
            }
            drawnPlayerBoard.add(columnBoard)
        }
        playerBoard.board = drawnPlayerBoard

        for (row in 0 until rivalBoard.boardHeight) {
            columnBoard = mutableListOf()
            for (col in 0 until rivalBoard.boardWidth) {
                val actualCellBoard = BoardCellClass(CellDataClass(row, col), Assets.waterUntouched!!, Assets.waterOver!!, Assets.waterTouched!!, false, false)
                columnBoard.add(actualCellBoard)
            }
            drawnRivalBoard.add(columnBoard)
        }
        rivalBoard.board = drawnRivalBoard
    }

    val carrier = ShipClass("Carrier",((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + cellSide) / cellSide).toInt(), Assets.CARRIER_LENGTH, true, false, Assets.horizontalCarrier!!, Assets.verticalCarrier!!, Assets.horizontalCarrierFlames!!, Assets.verticalCarrierFlames!!, false)
    val battleshipOne = ShipClass("BattleshipOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 3)) / cellSide).toInt(), Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false)
    val battleshipTwo = ShipClass("BattleshipTwo", ((originX2 - xOffset + (cellSide * 5)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 3)) / cellSide).toInt(), Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false)
    val shiprescueOne = ShipClass("ShiprescueOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false)
    val shiprescueTwo = ShipClass("ShiprescueTwo", ((originX2 - xOffset + (cellSide * 4)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false)
    val shiprescueThree = ShipClass("ShiprescueThree", ((originX2 - xOffset + (cellSide * 7)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false)
    val shippatrolOne = ShipClass("ShippatrolOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)
    val shippatrolTwo = ShipClass("ShippatrolTwo", ((originX2 - xOffset + (cellSide * 3)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)
    val shippatrolThree = ShipClass("ShippatrolThree", ((originX2 - xOffset + (cellSide * 5)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)
    val shippatrolFour = ShipClass("ShippatrolFour", ((originX2 - xOffset + (cellSide * 7)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)

    val carrierDefaultPosition : CellDataClass = CellDataClass((carrier.x), (carrier.y))
    val battleshipOneDefaultPosition : CellDataClass = CellDataClass((battleshipOne.x), (battleshipOne.y))
    val battleshipTwoDefaultPosition : CellDataClass = CellDataClass((battleshipTwo.x), (battleshipTwo.y))
    val shiprescueOneDefaultPosition : CellDataClass = CellDataClass((shiprescueOne.x), (shiprescueOne.y))
    val shiprescueTwoDefaultPosition : CellDataClass = CellDataClass((shiprescueTwo.x), (shiprescueTwo.y))
    val shiprescueThreeDefaultPosition : CellDataClass = CellDataClass((shiprescueThree.x), (shiprescueThree.y))
    val shippatrolOneDefaultPosition : CellDataClass = CellDataClass((shippatrolOne.x), (shippatrolOne.y))
    val shippatrolTwoDefaultPosition : CellDataClass = CellDataClass((shippatrolTwo.x), (shippatrolTwo.y))
    val shippatrolThreeDefaultPosition : CellDataClass = CellDataClass((shippatrolThree.x), (shippatrolThree.y))
    val shippatrolFourDefaultPosition : CellDataClass = CellDataClass((shippatrolFour.x), (shippatrolFour.y))

    val rcarrier = ShipClass("Carrier",0, 0, Assets.CARRIER_LENGTH, true, false, Assets.horizontalCarrier!!, Assets.verticalCarrier!!, Assets.horizontalCarrierFlames!!, Assets.verticalCarrierFlames!!, false)
    val rbattleshipOne = ShipClass("BattleshipOne", 0, 0, Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false)
    val rbattleshipTwo = ShipClass("BattleshipTwo", 0, 0, Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false)
    val rshiprescueOne = ShipClass("ShiprescueOne", 0, 0, Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false)
    val rshiprescueTwo = ShipClass("ShiprescueTwo", 0, 0, Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false)
    val rshiprescueThree = ShipClass("ShiprescueThree", 0, 0, Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false)
    val rshippatrolOne = ShipClass("ShippatrolOne", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)
    val rshippatrolTwo = ShipClass("ShippatrolTwo", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)
    val rshippatrolThree = ShipClass("ShippatrolThree", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)
    val rshippatrolFour = ShipClass("ShippatrolFour", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false)


    var shipList : MutableList<ShipClass> = mutableListOf(carrier, battleshipOne, battleshipTwo, shiprescueOne, shiprescueTwo, shiprescueThree, shippatrolOne, shippatrolTwo, shippatrolThree, shippatrolFour)
    var rshipList : MutableList<ShipClass> = mutableListOf(rcarrier, rbattleshipOne, rbattleshipTwo, rshiprescueOne, rshiprescueTwo, rshiprescueThree, rshippatrolOne, rshippatrolTwo, rshippatrolThree, rshippatrolFour)
    var defaultShipPositions : MutableList<CellDataClass> = mutableListOf(carrierDefaultPosition, battleshipOneDefaultPosition, battleshipTwoDefaultPosition, shiprescueOneDefaultPosition, shiprescueTwoDefaultPosition, shiprescueThreeDefaultPosition, shippatrolOneDefaultPosition, shippatrolTwoDefaultPosition, shippatrolThreeDefaultPosition, shippatrolFourDefaultPosition)

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if (touchEvents != null) {
            for (event in touchEvents) {
                val correctedEventX : Int = ((event.x - xOffset) / cellSide).toInt()
                val correctedEventY : Int = ((event.y - yOffset) / cellSide).toInt()
                when (event.type) {
                    TouchHandler.TouchType.TOUCH_DOWN -> {
                        // ACTIONS FOR TOUCH_DOWN
                        // Placing ships phase
                        if (placingShips) {
                            draggedBoat = null
                            clickedShip = false
                            if (model.clickedOnBoat(CellDataClass(correctedEventX, correctedEventY), shipList)) {
                                clickedShip = true
                                draggedBoat = model.getClickedBoat(CellDataClass(correctedEventX, correctedEventY), shipList)!!
                            }
                        }

                        // Playing game phase
                    }

                    TouchHandler.TouchType.TOUCH_DRAGGED -> {
                        // ACTIONS FOR TOUCH_DRAGGED
                        // Placing ships phase
                        if (placingShips) {
                            draggingShip = true
                            if (clickedShip) {
                                model.moveBoat(CellDataClass(correctedEventX, correctedEventY), draggedBoat!!)

                                // Update cells
                                model.resetWaterCells(playerBoard)
                                if (model.checkPosition(CellDataClass(correctedEventX, correctedEventY), draggedBoat!!, playerBoard)) {
                                    model.changeWaterCells(playerBoard)
                                }

                                else {
                                    model.resetWaterCells(playerBoard)
                                    model.changeWaterCells(playerBoard)
                                }
                            }
                        }

                        // Playing game phase
                    }

                    TouchHandler.TouchType.TOUCH_UP -> {
                        // ACTIONS FOR TOUCH_UP
                        // Placing ships phase
                        if (placingShips) {
                            clickedShip = false
                            if (!draggingShip && draggedBoat != null) {
                                if (model.checkRotation(draggedBoat!!, playerBoard)) {
                                    model.resetBoardOfBoat(draggedBoat!!, playerBoard)
                                    model.rotateBoat(draggedBoat!!)
                                    model.colocateShip(draggedBoat!!, playerBoard)
                                }

                                model.resetWaterCells(playerBoard)
                                model.changeWaterCells(playerBoard)
                            }

                            else if (draggedBoat != null) {
                                if (!model.checkPosition(CellDataClass(correctedEventX, correctedEventY), draggedBoat!!, playerBoard)) {
                                    model.resetPosition(draggedBoat!!, defaultShipPositions)
                                    model.resetBoardOfBoat(draggedBoat!!, playerBoard)
                                    model.resetWaterCells(playerBoard)
                                }

                                else {
                                    if (!model.checkIfThereIsBoat(draggedBoat!!, playerBoard)) {
                                        model.resetBoardOfBoat(draggedBoat!!, playerBoard)
                                        model.colocateShip(draggedBoat!!, playerBoard)
                                    }
                                    else {
                                        model.resetBoardOfBoat(draggedBoat!!, playerBoard)
                                        model.resetPosition(draggedBoat!!, defaultShipPositions)
                                    }
                                }
                                model.resetWaterCells(playerBoard)
                                model.changeWaterCells(playerBoard)
                                draggingShip = false
                            }
                        }

                        // Ships placed
                        if (drawButton) {
                            if (model.checkIfButtonPressed(CellDataClass(correctedEventX, correctedEventY))) {
                                placingShips = false
                                rival = true
                                drawButton = false

                                rivalModel.generateNewBoard(rivalBoard, rshipList)
                                playingGame = true
                                gameState = "Play game"
                            }
                        }

                        // Playing game phase
                    }
                }
            }
        }

        // ACTIONS WHICH NOT DEPEND ON USER'S TOUCHES (UPDATES)
        // Placing ships phase
        if (placingShips) {
            drawButton = model.checkBoatsPlaced(shipList)
        }

        // Playing game phase
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(Color.WHITE)

        drawBoard(player, rival)
        drawText(gameState)
        drawBoats()
        /*if (playingGame)
            drawRBoats()
        */
        drawBattleButton(drawButton)

        return graphics.frameBuffer
    }

    private fun drawBoard(player : Boolean, rival : Boolean) {
        if (player) {
            for (list in drawnPlayerBoard) {
                for (cell in list) {
                    graphics.drawBitmap(cell.activeBitmap, originX + (cellSide * cell.cell.x), originY + (cellSide * cell.cell.y))
                }
            }
        }

        if (rival) {
            for (list in drawnRivalBoard) {
                for (cell in list) {
                    graphics.drawBitmap(cell.activeBitmap, originX2 + (cellSide * cell.cell.x), originY2 + (cellSide * cell.cell.y))
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

        else if (state == "Prepare battle") {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Ready to battle?")
        }

        else if (state == "Play game") {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Sink the enemy's navy!")
        }

        else if (state == "Win") {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Congrats!! You won the game!")
        }

        else if (state == "Lose") {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Oh no!! You lost!")
        }
    }

    private fun drawBoats() {
        graphics.drawBitmap(carrier.activeBitmap, carrier.x * cellSide + xOffset, carrier.y * cellSide + yOffset)
        graphics.drawBitmap(battleshipOne.activeBitmap, battleshipOne.x * cellSide + xOffset, battleshipOne.y * cellSide + yOffset)
        graphics.drawBitmap(battleshipTwo.activeBitmap, battleshipTwo.x * cellSide + xOffset, battleshipTwo.y * cellSide + yOffset)
        graphics.drawBitmap(shiprescueOne.activeBitmap, shiprescueOne.x * cellSide + xOffset, shiprescueOne.y * cellSide + yOffset)
        graphics.drawBitmap(shiprescueTwo.activeBitmap, shiprescueTwo.x * cellSide + xOffset, shiprescueTwo.y * cellSide + yOffset)
        graphics.drawBitmap(shiprescueThree.activeBitmap, shiprescueThree.x * cellSide + xOffset, shiprescueThree.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolOne.activeBitmap, shippatrolOne.x * cellSide + xOffset, shippatrolOne.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolTwo.activeBitmap, shippatrolTwo.x * cellSide + xOffset, shippatrolTwo.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolThree.activeBitmap, shippatrolThree.x * cellSide + xOffset, shippatrolThree.y * cellSide + yOffset)
        graphics.drawBitmap(shippatrolFour.activeBitmap, shippatrolFour.x * cellSide + xOffset, shippatrolFour.y * cellSide + yOffset)
    }

    private fun drawRBoats() {
        graphics.drawBitmap(rcarrier.activeBitmap, rcarrier.x * cellSide + xOffset, rcarrier.y * cellSide + yOffset)
        graphics.drawBitmap(rbattleshipOne.activeBitmap, rbattleshipOne.x * cellSide + xOffset, rbattleshipOne.y * cellSide + yOffset)
        graphics.drawBitmap(rbattleshipTwo.activeBitmap, rbattleshipTwo.x * cellSide + xOffset, rbattleshipTwo.y * cellSide + yOffset)
        graphics.drawBitmap(rshiprescueOne.activeBitmap, rshiprescueOne.x * cellSide + xOffset, rshiprescueOne.y * cellSide + yOffset)
        graphics.drawBitmap(rshiprescueTwo.activeBitmap, rshiprescueTwo.x * cellSide + xOffset, rshiprescueTwo.y * cellSide + yOffset)
        graphics.drawBitmap(rshiprescueThree.activeBitmap, rshiprescueThree.x * cellSide + xOffset, rshiprescueThree.y * cellSide + yOffset)
        graphics.drawBitmap(rshippatrolOne.activeBitmap, rshippatrolOne.x * cellSide + xOffset, rshippatrolOne.y * cellSide + yOffset)
        graphics.drawBitmap(rshippatrolTwo.activeBitmap, rshippatrolTwo.x * cellSide + xOffset, rshippatrolTwo.y * cellSide + yOffset)
        graphics.drawBitmap(rshippatrolThree.activeBitmap, rshippatrolThree.x * cellSide + xOffset, rshippatrolThree.y * cellSide + yOffset)
        graphics.drawBitmap(rshippatrolFour.activeBitmap, rshippatrolFour.x * cellSide + xOffset, rshippatrolFour.y * cellSide + yOffset)
    }

    private fun drawBattleButton(draw : Boolean) {
        if (draw) {
            graphics.drawRect(originX2 + (cellSide * 5), originY2 + (cellSide * 6), cellSide*4, cellSide * 4, Color.parseColor("#FFBF00"))
            graphics.setTextColor(Color.BLACK)
            graphics.drawText(originX2 + (cellSide * 7), (originY2 + (cellSide * 8.25)).toFloat(), "Battle!")
            gameState = "Prepare battle"
        }
    }
}