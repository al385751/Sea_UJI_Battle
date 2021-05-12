package com.example.a2_seaujibattle.gameActivity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.example.a2_seaujibattle.controller.SeaUjiBattleController
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.IGameController

class SeaUjiBattle : GameActivity() {
    var soundEffects : String = ""
    var smartOpponent : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        soundEffects = intent.getStringExtra("SoundEffects")!!
        smartOpponent = intent.getStringExtra("SmartOpponent")!!
        return SeaUjiBattleController(displayMetrics.widthPixels, displayMetrics.heightPixels, applicationContext, soundEffects, smartOpponent)
    }
}
