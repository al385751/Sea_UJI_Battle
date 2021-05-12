package com.example.a2_seaujibattle.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.core.content.res.ResourcesCompat
import com.example.a2_seaujibattle.R
import com.example.a2_seaujibattle.additionalClasses.*
import com.example.a2_seaujibattle.model.Model
import com.example.a2_seaujibattle.model.SeaBattleAction
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import kotlin.math.min


private const val TOTAL_CELLS_WIDTH  = 24
private const val TOTAL_CELLS_HEIGHT  = 14

class SeaUjiBattleController(width: Int, height: Int, applicationContext: Context, _soundEffects: String, _smartOpponent: String) : IGameController {
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

    val soundEffects : Boolean = _soundEffects.toBoolean()
    val smartOpponent : Boolean = _smartOpponent.toBoolean()

    var draggingShip : Boolean = false
    var clickedShip : Boolean = false
    var draggedBoat : ShipClass? = null
    var hittedBoat : ShipClass? = null
    var hittedCells : MutableList<BoardCellClass> = mutableListOf()

    var drawButton : Boolean = false

    var player : Boolean = true
    var rival : Boolean = false

    var soundPool : SoundPool
    var waterSound : Int
    var explosionSound : Int

    init {
        Assets.loadDrawableAssets(applicationContext)
        Assets.createResizedAssets(applicationContext, cellSide.toInt())

        var columnBoard : MutableList<BoardCellClass>
        for (row in 0 until playerBoard.boardHeight) {
            columnBoard = mutableListOf()
            for (col in 0 until playerBoard.boardWidth) {
                val actualCellBoard = BoardCellClass(CellDataClass(row, col), Assets.waterUntouched!!, Assets.waterOver!!, Assets.waterTouched!!, Assets.waterExploded!!, false, false)
                columnBoard.add(actualCellBoard)
            }
            drawnPlayerBoard.add(columnBoard)
        }
        playerBoard.board = drawnPlayerBoard

        for (row in 0 until rivalBoard.boardHeight) {
            columnBoard = mutableListOf()
            for (col in 0 until rivalBoard.boardWidth) {
                val actualCellBoard = BoardCellClass(CellDataClass(row, col), Assets.waterUntouched!!, Assets.waterOver!!, Assets.waterTouched!!, Assets.waterExploded!!, false, false)
                columnBoard.add(actualCellBoard)
            }
            drawnRivalBoard.add(columnBoard)
        }
        rivalBoard.board = drawnRivalBoard

        val audioAttributes : AudioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build()

        soundPool = SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()

        waterSound = soundPool.load(applicationContext, R.raw.watersplash, 1)
        explosionSound = soundPool.load(applicationContext, R.raw.explosion, 1)
    }

    val model : Model = Model(soundPool)

    val carrier = ShipClass("Carrier",((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + cellSide) / cellSide).toInt(), Assets.CARRIER_LENGTH, true, false, Assets.horizontalCarrier!!, Assets.verticalCarrier!!, Assets.horizontalCarrierFlames!!, Assets.verticalCarrierFlames!!, false, 0)
    val battleshipOne = ShipClass("BattleshipOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 3)) / cellSide).toInt(), Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false, 0)
    val battleshipTwo = ShipClass("BattleshipTwo", ((originX2 - xOffset + (cellSide * 5)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 3)) / cellSide).toInt(), Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false, 0)
    val shiprescueOne = ShipClass("ShiprescueOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false, 0)
    val shiprescueTwo = ShipClass("ShiprescueTwo", ((originX2 - xOffset + (cellSide * 4)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false, 0)
    val shiprescueThree = ShipClass("ShiprescueThree", ((originX2 - xOffset + (cellSide * 7)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 5)) / cellSide).toInt(), Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false, 0)
    val shippatrolOne = ShipClass("ShippatrolOne", ((originX2 - xOffset + cellSide) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)
    val shippatrolTwo = ShipClass("ShippatrolTwo", ((originX2 - xOffset + (cellSide * 3)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)
    val shippatrolThree = ShipClass("ShippatrolThree", ((originX2 - xOffset + (cellSide * 5)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)
    val shippatrolFour = ShipClass("ShippatrolFour", ((originX2 - xOffset + (cellSide * 7)) / cellSide).toInt(), ((originY2 - yOffset + (cellSide * 7)) / cellSide).toInt(), Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)

    val rcarrier = ShipClass("Carrier",0, 0, Assets.CARRIER_LENGTH, true, false, Assets.horizontalCarrier!!, Assets.verticalCarrier!!, Assets.horizontalCarrierFlames!!, Assets.verticalCarrierFlames!!, false, 0)
    val rbattleshipOne = ShipClass("BattleshipOne", 0, 0, Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false, 0)
    val rbattleshipTwo = ShipClass("BattleshipTwo", 0, 0, Assets.BATTLESHIP_LENGTH, true, false, Assets.horizontalBattleship!!, Assets.verticalBattleship!!, Assets.horizontalBattleshipFlames!!, Assets.verticalBattleshipFlames!!, false, 0)
    val rshiprescueOne = ShipClass("ShiprescueOne", 0, 0, Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false, 0)
    val rshiprescueTwo = ShipClass("ShiprescueTwo", 0, 0, Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false, 0)
    val rshiprescueThree = ShipClass("ShiprescueThree", 0, 0, Assets.SHIP_RESCUE_LENGTH, true, false, Assets.horizontalShipRescue!!, Assets.verticalShipRescue!!, Assets.horizontalShipRescueFlames!!, Assets.verticalShipRescueFlames!!, false, 0)
    val rshippatrolOne = ShipClass("ShippatrolOne", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)
    val rshippatrolTwo = ShipClass("ShippatrolTwo", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)
    val rshippatrolThree = ShipClass("ShippatrolThree", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)
    val rshippatrolFour = ShipClass("ShippatrolFour", 0, 0, Assets.SHIP_PATROL_LENGTH, true, false, Assets.horizontalShipPatrol!!, Assets.verticalShipPatrol!!, Assets.horizontalShipPatrolFlames!!, Assets.verticalShipPatrolFlames!!, false, 0)

    var shipList : MutableList<ShipClass> = mutableListOf(carrier, battleshipOne, battleshipTwo, shiprescueOne, shiprescueTwo, shiprescueThree, shippatrolOne, shippatrolTwo, shippatrolThree, shippatrolFour)
    var rshipList : MutableList<ShipClass> = mutableListOf(rcarrier, rbattleshipOne, rbattleshipTwo, rshiprescueOne, rshiprescueTwo, rshiprescueThree, rshippatrolOne, rshippatrolTwo, rshippatrolThree, rshippatrolFour)

    val waterDrop : Animation = Animation(Assets.waterSplash!!, 0, 0)

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if (touchEvents != null) {
            for (event in touchEvents) {
                val correctedEventX : Int = ((event.x - xOffset) / cellSide).toInt()
                val correctedEventY : Int = ((event.y - yOffset) / cellSide).toInt()
                when (event.type) {
                    TouchHandler.TouchType.TOUCH_DOWN -> {
                        // ACTIONS FOR TOUCH_DOWN
                        // Placing ships phase
                        if (model.state == SeaBattleAction.PLACE_SHIPS) {
                            draggedBoat = null
                            clickedShip = false
                            if (model.clickedOnBoat(CellDataClass(correctedEventX, correctedEventY), shipList)) {
                                clickedShip = true
                                draggedBoat = model.getClickedBoat(CellDataClass(correctedEventX, correctedEventY), shipList)!!
                            }
                        }

                        // Playing game phase
                        if (model.state == SeaBattleAction.PLAYER_TURN) {
                            if (rivalBoard.coordInsideBoard(CellDataClass(correctedEventX, correctedEventY))) {
                                val clickedCell = rivalBoard.getCellInSecondBoard(CellDataClass(correctedEventX, correctedEventY))
                                if (clickedCell!!.hasBoat == "" && !clickedCell.isHitted) {
                                    if (soundEffects)
                                        model.playSelectedSound(waterSound)
                                    model.changeAnimationPosition(waterDrop, CellDataClass(correctedEventX, correctedEventY), SeaBattleAction.WAITING_WATER)
                                    clickedCell.activeBitmap = clickedCell.hittedWater
                                    clickedCell.isHitted = true
                                }

                                else if (clickedCell.hasBoat != "" && !clickedCell.isHitted) {
                                    if (soundEffects)
                                        model.playSelectedSound(explosionSound)
                                    hittedBoat = model.getClickedBoat(CellDataClass(correctedEventX, correctedEventY), rshipList)!!
                                    if (hittedBoat!!.hits == hittedBoat!!.shipLength - 1) {
                                        hittedCells = rivalBoard.whereIsPlaced(hittedBoat!!)!!
                                        for (cell in hittedCells) {
                                            model.changeAnimationPosition(cell.explosionAnimation, CellDataClass(cell.cell.x + 13, cell.cell.y + 2), SeaBattleAction.WAITING_EXPLOSION)
                                        }
                                        hittedBoat!!.isSunk = true
                                    }
                                    else {
                                        hittedCells = mutableListOf(clickedCell)
                                        model.changeAnimationPosition(hittedCells[0].explosionAnimation, CellDataClass(correctedEventX, correctedEventY), SeaBattleAction.WAITING_EXPLOSION)
                                        hittedBoat!!.hits++
                                    }
                                    clickedCell.activeBitmap = clickedCell.explotedWater
                                    clickedCell.isHitted = true
                                }
                            }
                        }
                    }

                    TouchHandler.TouchType.TOUCH_DRAGGED -> {
                        // ACTIONS FOR TOUCH_DRAGGED
                        // Placing ships phase
                        if (model.state == SeaBattleAction.PLACE_SHIPS) {
                            draggingShip = true
                            if (clickedShip) {
                                model.moveBoat(CellDataClass(correctedEventX, correctedEventY), draggedBoat!!)

                                // Update cells
                                model.resetWaterCells(playerBoard)
                                if (model.checkPosition(CellDataClass(correctedEventX, correctedEventY), draggedBoat!!, playerBoard) && !model.checkIfThereIsBoat(draggedBoat!!, playerBoard)) {
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
                        if (model.state == SeaBattleAction.PLACE_SHIPS) {
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
                                    model.resetPosition(draggedBoat!!, playerBoard)
                                    model.resetBoardOfBoat(draggedBoat!!, playerBoard)
                                    model.resetWaterCells(playerBoard)
                                }

                                else {
                                    if (!model.checkIfThereIsBoat(draggedBoat!!, playerBoard)) {
                                        model.resetBoardOfBoat(draggedBoat!!, playerBoard)
                                        model.colocateShip(draggedBoat!!, playerBoard)
                                    }
                                    else {
                                        model.resetPosition(draggedBoat!!, playerBoard)
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
                                rival = true
                                drawButton = false

                                model.generateNewBoard(rivalBoard, rshipList)
                            }
                        }

                        // Playing game phase

                    }
                }
            }
        }

        // ACTIONS WHICH NOT DEPEND ON USER'S TOUCHES (UPDATES)
        // Placing ships phase
        if (model.state == SeaBattleAction.PLACE_SHIPS) {
            drawButton = model.checkBoatsPlaced(shipList)
        }

        // Playing game phase
        if (model.state == SeaBattleAction.WAITING_WATER) {
            if (!waterDrop.bitmap.isEnded)
                waterDrop.bitmap.update(deltaTime)
            else {
                model.changeGameState(SeaBattleAction.PLAYER_TURN)
                waterDrop.bitmap.restart()
            }
        }

        else if (model.state == SeaBattleAction.WAITING_EXPLOSION) {
            if (hittedBoat!!.hits != hittedBoat!!.shipLength) {
                if (!hittedCells[0].explosionAnimation.bitmap.isEnded)
                    hittedCells[0].explosionAnimation.bitmap.update(deltaTime)
                else {
                    model.changeGameState(SeaBattleAction.PLAYER_TURN)
                    hittedCells[0].explosionAnimation.bitmap.restart()
                }
            }

            else {
                for (cell in hittedCells) {
                    if (cell.explosionAnimation.bitmap.isEnded)
                        cell.explosionAnimation.bitmap.update(deltaTime)
                    else {
                        model.changeGameState(SeaBattleAction.PLAYER_TURN)
                        cell.explosionAnimation.bitmap.restart()
                        hittedBoat!!.isSunk = true
                    }
                }
            }
        }
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(Color.WHITE)

        drawBoard(player, rival)
        drawText(model.state)
        drawBoats()

        drawBattleButton(drawButton)

        if (model.state == SeaBattleAction.PLAYER_TURN)
            drawArrows()

        if (model.state == SeaBattleAction.WAITING_WATER)
            drawWaterDrop()

        drawRBoats()

        if (model.state == SeaBattleAction.WAITING_EXPLOSION) {
            for (cell in hittedCells){
                drawFireExplosion(cell)
            }
        }

        return graphics.frameBuffer
    }

    private fun drawFireExplosion(cell : BoardCellClass)  {
        graphics.drawBitmap(cell.explosionAnimation.bitmap.currentFrame, cell.explosionAnimation.x * cellSide + xOffset, cell.explosionAnimation.y * cellSide + yOffset)
    }

    private fun drawWaterDrop() {
        graphics.drawBitmap(waterDrop.bitmap.currentFrame, waterDrop.x * cellSide + xOffset, waterDrop.y * cellSide + yOffset)
    }

    private fun drawArrows() {
        graphics.drawBitmap(Assets.yellowArrows, (originX + ((playerBoard.boardWidth - 2) * cellSide)).toFloat(), (originY + ((playerBoard.boardHeight + 0.5) * cellSide)).toFloat())
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

    private fun drawText(state: SeaBattleAction) {
        graphics.setTextSize(90)
        graphics.setTextAlign(Paint.Align.CENTER)
        graphics.setTextColor(Color.parseColor("#FFBF00"))
        graphics.setTypeface(myTypeface)

        if (state == SeaBattleAction.PLACE_SHIPS){
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Time to place your ships!!")
        }

        else if (state == SeaBattleAction.PLAYER_TURN) {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Your turn! Sink the enemy's navy!")
        }

        else if (state == SeaBattleAction.WAITING_WATER) {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "WATER!")
        }

        else if (state == SeaBattleAction.COMPUTER_TURN) {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Watch out! It the turn or your rival!")
        }

        else if (state == SeaBattleAction.WAITING_EXPLOSION) {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "BOAT HIT!")
        }

        else if (state == SeaBattleAction.END_WIN) {
            graphics.drawText(originX + (cellSide * 11), (originY - (cellSide * 0.75)).toFloat(), "Congrats!! You won the game!")
        }

        else if (state == SeaBattleAction.END_LOSE) {
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
        if (rcarrier.isSunk) {
            if (rcarrier.isHorizontal)
                rcarrier.activeBitmap = rcarrier.horizontalFlamesBoat
            else
                rcarrier.activeBitmap = rcarrier.verticalFlamesBoat
            graphics.drawBitmap(rcarrier.activeBitmap, rcarrier.x * cellSide + xOffset, rcarrier.y * cellSide + yOffset)
        }

        if (rbattleshipOne.isSunk) {
            if (rbattleshipOne.isHorizontal)
                rbattleshipOne.activeBitmap = rbattleshipOne.horizontalFlamesBoat
            else
                rbattleshipOne.activeBitmap = rbattleshipOne.verticalFlamesBoat
            graphics.drawBitmap(rbattleshipOne.activeBitmap, rbattleshipOne.x * cellSide + xOffset, rbattleshipOne.y * cellSide + yOffset)
        }

        if (rbattleshipTwo.isSunk) {
            if (rbattleshipTwo.isHorizontal)
                rbattleshipTwo.activeBitmap = rbattleshipTwo.horizontalFlamesBoat
            else
                rbattleshipTwo.activeBitmap = rbattleshipTwo.verticalFlamesBoat
            graphics.drawBitmap(rbattleshipTwo.activeBitmap, rbattleshipTwo.x * cellSide + xOffset, rbattleshipTwo.y * cellSide + yOffset)
        }

        if (rshiprescueOne.isSunk) {
            if (rshiprescueOne.isHorizontal)
                rshiprescueOne.activeBitmap = rshiprescueOne.horizontalFlamesBoat
            else
                rshiprescueOne.activeBitmap = rshiprescueOne.verticalFlamesBoat
            graphics.drawBitmap(rshiprescueOne.activeBitmap, rshiprescueOne.x * cellSide + xOffset, rshiprescueOne.y * cellSide + yOffset)
        }

        if (rshiprescueTwo.isSunk) {
            if (rshiprescueTwo.isHorizontal)
                rshiprescueTwo.activeBitmap = rshiprescueTwo.horizontalFlamesBoat
            else
                rshiprescueTwo.activeBitmap = rshiprescueTwo.verticalFlamesBoat
            graphics.drawBitmap(rshiprescueTwo.activeBitmap, rshiprescueTwo.x * cellSide + xOffset, rshiprescueTwo.y * cellSide + yOffset)
        }

        if (rshiprescueThree.isSunk) {
            if (rshiprescueThree.isHorizontal)
                rshiprescueThree.activeBitmap = rshiprescueThree.horizontalFlamesBoat
            else
                rshiprescueThree.activeBitmap = rshiprescueThree.verticalFlamesBoat
            graphics.drawBitmap(rshiprescueThree.activeBitmap, rshiprescueThree.x * cellSide + xOffset, rshiprescueThree.y * cellSide + yOffset)
        }

        if (rshippatrolOne.isSunk) {
            if (rshippatrolOne.isHorizontal)
                rshippatrolOne.activeBitmap = rshippatrolOne.horizontalFlamesBoat
            else
                rshippatrolOne.activeBitmap = rshippatrolOne.verticalFlamesBoat
            graphics.drawBitmap(rshippatrolOne.activeBitmap, rshippatrolOne.x * cellSide + xOffset, rshippatrolOne.y * cellSide + yOffset)
        }

        if (rshippatrolTwo.isSunk) {
            if (rshippatrolTwo.isHorizontal)
                rshippatrolTwo.activeBitmap = rshippatrolTwo.horizontalFlamesBoat
            else
                rshippatrolTwo.activeBitmap = rshippatrolTwo.verticalFlamesBoat
            graphics.drawBitmap(rshippatrolTwo.activeBitmap, rshippatrolTwo.x * cellSide + xOffset, rshippatrolTwo.y * cellSide + yOffset)
        }

        if (rshippatrolThree.isSunk) {
            if (rshippatrolThree.isHorizontal)
                rshippatrolThree.activeBitmap = rshippatrolThree.horizontalFlamesBoat
            else
                rshippatrolThree.activeBitmap = rshippatrolThree.verticalFlamesBoat
            graphics.drawBitmap(rshippatrolThree.activeBitmap, rshippatrolThree.x * cellSide + xOffset, rshippatrolThree.y * cellSide + yOffset)
        }

        if (rshippatrolFour.isSunk) {
            if (rshippatrolFour.isHorizontal)
                rshippatrolFour.activeBitmap = rshippatrolFour.horizontalFlamesBoat
            else
                rshippatrolFour.activeBitmap = rshippatrolFour.verticalFlamesBoat
            graphics.drawBitmap(rshippatrolFour.activeBitmap, rshippatrolFour.x * cellSide + xOffset, rshippatrolFour.y * cellSide + yOffset)
        }
    }

    private fun drawBattleButton(draw : Boolean) {
        if (draw) {
            graphics.drawRect(originX2 + (cellSide * 5), originY2 + (cellSide * 6), cellSide*4, cellSide * 4, Color.parseColor("#FFBF00"))
            graphics.setTextColor(Color.BLACK)
            graphics.drawText(originX2 + (cellSide * 7), (originY2 + (cellSide * 8.25)).toFloat(), "Battle!")
        }
    }
}