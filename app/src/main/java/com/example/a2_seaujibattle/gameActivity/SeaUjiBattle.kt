package com.example.a2_seaujibattle.gameActivity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.a2_seaujibattle.R
import com.example.a2_seaujibattle.controller.SeaUjiBattleController
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.IGameController

class SeaUjiBattle : GameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        return SeaUjiBattleController(displayMetrics.widthPixels, displayMetrics.heightPixels, applicationContext)
    }
}