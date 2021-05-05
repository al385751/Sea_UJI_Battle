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

    var horizontalCarrier : Bitmap? = null
    var verticalCarrier : Bitmap? = null
    var horizontalBattleship : Bitmap? = null
    var verticalBattleship : Bitmap? = null
    var horizontalShipRescue : Bitmap? = null
    var verticalShipRescue : Bitmap? = null
    var horizontalShipPatrol : Bitmap? = null
    var verticalShipPatrol : Bitmap? = null

    private var splash : SpriteSheet? = null
    var waterSplash : AnimatedBitmap? = null
    private var explosion : SpriteSheet? = null
    var fireExplosion : AnimatedBitmap? = null

    fun loadDrawableAssets(context: Context) {
        val resources : Resources = context.resources

        // Falta algo

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

        // Falta algo

        horizontalCarrier?.recycle()
        horizontalCarrier = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.carrierhorizontal),
            cellSize * CARRIER_LENGTH, cellSize, true)
        verticalCarrier?.recycle()
        verticalCarrier = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.carriervertical),
            cellSize * CARRIER_LENGTH, cellSize, true)

        horizontalBattleship?.recycle()
        horizontalBattleship = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.battleshiphorizontal),
            cellSize * BATTLESHIP_LENGTH, cellSize, true)
        verticalBattleship?.recycle()
        verticalBattleship = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.battleshipvertical),
            cellSize * BATTLESHIP_LENGTH, cellSize, true)

        horizontalShipRescue?.recycle()
        horizontalShipRescue = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shiprescuehorizontal),
            cellSize * SHIP_RESCUE_LENGTH, cellSize, true)
        verticalShipRescue?.recycle()
        verticalShipRescue = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shiprescuevertical),
            cellSize * SHIP_RESCUE_LENGTH, cellSize, true)

        horizontalShipPatrol?.recycle()
        horizontalShipPatrol = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shippatrolhorizontal),
            cellSize * SHIP_PATROL_LENGTH, cellSize, true)
        verticalShipPatrol?.recycle()
        verticalShipPatrol = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.shippatrolvertical),
            cellSize * SHIP_PATROL_LENGTH, cellSize, true)

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