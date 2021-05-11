package com.example.a2_seaujibattle.controller

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.a2_seaujibattle.R
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    const val CARRIER_LENGTH = 4
    const val BATTLESHIP_LENGTH = 3
    const val SHIP_RESCUE_LENGTH = 2
    const val SHIP_PATROL_LENGTH = 1

    private const val SPLASH_ROWS = 2
    private const val SPLASH_COLUMNS = 4
    private const val SPLASH_WIDTH = 62
    private const val SPLASH_HEIGHT = 33

    private const val EXPLOSION_ROWS = 5
    private const val EXPLOSION_COLUMNS = 10
    private const val EXPLOSION_WIDTH = 10
    private const val EXPLOSION_HEIGHT = 10

    var waterUntouched : Bitmap? = null
    var waterTouched : Bitmap? = null
    var waterOver : Bitmap? = null

    var horizontalCarrier : Bitmap? = null
    var horizontalCarrierFlames : Bitmap? = null
    var verticalCarrier : Bitmap? = null
    var verticalCarrierFlames : Bitmap? = null

    var horizontalBattleship : Bitmap? = null
    var horizontalBattleshipFlames : Bitmap? = null
    var verticalBattleship : Bitmap? = null
    var verticalBattleshipFlames : Bitmap? = null

    var horizontalShipRescue : Bitmap? = null
    var horizontalShipRescueFlames : Bitmap? = null
    var verticalShipRescue : Bitmap? = null
    var verticalShipRescueFlames : Bitmap? = null

    var horizontalShipPatrol : Bitmap? = null
    var horizontalShipPatrolFlames : Bitmap? = null
    var verticalShipPatrol : Bitmap? = null
    var verticalShipPatrolFlames : Bitmap? = null

    var yellowArrows : Bitmap? = null

    private var splash : SpriteSheet? = null
    var waterSplash : AnimatedBitmap? = null
    private var explosion : SpriteSheet? = null
    var fireExplosion : AnimatedBitmap? = null

    fun loadDrawableAssets(context: Context) {
        val resources : Resources = context.resources

        if (splash == null) {
            val sheet = BitmapFactory.decodeResource(resources, R.drawable.splashanimation)
            splash = SpriteSheet(sheet, SPLASH_HEIGHT, SPLASH_WIDTH)
        }

        if (explosion == null) {
            val sheet = BitmapFactory.decodeResource(resources, R.drawable.explosionanimation)
            explosion = SpriteSheet(sheet, EXPLOSION_HEIGHT, EXPLOSION_WIDTH)
        }
    }

    fun createResizedAssets(context: Context, cellSize : Int) {
        val resources : Resources = context.resources

        waterUntouched?.recycle()
        waterUntouched = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.wateruntouched),
                (cellSize * 1.05).toInt(), (cellSize * 1.05).toInt(), true)

        waterTouched?.recycle()
        waterTouched = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.watertouched),
                (cellSize * 1.05).toInt(), (cellSize * 1.05).toInt(), true)

        waterOver?.recycle()
        waterOver = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.waterover),
                (cellSize * 1.05).toInt(), (cellSize * 1.05).toInt(), true)

        horizontalCarrier?.recycle()
        horizontalCarrier = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.carrierhorizontal),
            cellSize * CARRIER_LENGTH, cellSize, true)
        horizontalCarrierFlames?.recycle()
        horizontalCarrierFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.carrierhorizontalflames),
                cellSize * CARRIER_LENGTH, cellSize, true)
        verticalCarrier?.recycle()
        verticalCarrier = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.carriervertical),
            cellSize, cellSize * CARRIER_LENGTH, true)
        verticalCarrierFlames?.recycle()
        verticalCarrierFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.carrierverticalflames),
                cellSize, cellSize * CARRIER_LENGTH, true)

        horizontalBattleship?.recycle()
        horizontalBattleship = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.battleshiphorizontal),
            cellSize * BATTLESHIP_LENGTH, cellSize, true)
        horizontalBattleshipFlames?.recycle()
        horizontalBattleshipFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.battleshiphorizontalflames),
                cellSize * BATTLESHIP_LENGTH, cellSize, true)
        verticalBattleship?.recycle()
        verticalBattleship = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.battleshipvertical),
            cellSize, cellSize * BATTLESHIP_LENGTH, true)
        verticalBattleshipFlames?.recycle()
        verticalBattleshipFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.battleshipverticalflames),
                cellSize, cellSize * BATTLESHIP_LENGTH, true)

        horizontalShipRescue?.recycle()
        horizontalShipRescue = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shiprescuehorizontal),
            cellSize * SHIP_RESCUE_LENGTH, cellSize, true)
        horizontalShipRescueFlames?.recycle()
        horizontalShipRescueFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.shiprescuehorizontalflames),
                cellSize * SHIP_RESCUE_LENGTH, cellSize, true)
        verticalShipRescue?.recycle()
        verticalShipRescue = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shiprescuevertical),
            cellSize, cellSize * SHIP_RESCUE_LENGTH, true)
        verticalShipRescueFlames?.recycle()
        verticalShipRescueFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.shiprescueverticalflames),
                cellSize, cellSize * SHIP_RESCUE_LENGTH, true)

        horizontalShipPatrol?.recycle()
        horizontalShipPatrol = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shippatrolhorizontal),
            cellSize * SHIP_PATROL_LENGTH, cellSize, true)
        horizontalShipPatrolFlames?.recycle()
        horizontalShipPatrolFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.shippatrolhorizontalflames),
                cellSize * SHIP_PATROL_LENGTH, cellSize, true)
        verticalShipPatrol?.recycle()
        verticalShipPatrol = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shippatrolvertical),
            cellSize, cellSize * SHIP_PATROL_LENGTH, true)
        verticalShipPatrolFlames?.recycle()
        verticalShipPatrolFlames = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.shippatrolverticalflames),
                cellSize, cellSize * SHIP_PATROL_LENGTH, true)

        yellowArrows?.recycle()
        yellowArrows = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.yellowarrows),
                cellSize * 6, (cellSize * 1.05).toInt(), true)

        val framesWater = ArrayList<Bitmap>()
        waterSplash?.recycle()
        for (row in 0 until SPLASH_ROWS) {
            splash?.let { framesWater.addAll(it.getScaledRow(row, SPLASH_COLUMNS, cellSize, cellSize)) }
        }
        waterSplash = AnimatedBitmap(2.0f, false, *framesWater.toTypedArray())

        val framesFire = ArrayList<Bitmap>()
        fireExplosion?.recycle()
        for (row in 0 until EXPLOSION_ROWS) {
            explosion?.let { framesFire.addAll(it.getScaledRow(row, EXPLOSION_COLUMNS, cellSize, cellSize)) }
        }
        fireExplosion = AnimatedBitmap(2.0f, false, *framesFire.toTypedArray())
    }
}