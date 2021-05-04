package com.example.a2_seaujibattle

import android.util.DisplayMetrics
import com.example.a2_seaujibattle.controller.SeaUjiBattleController
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.IGameController

class SeaUjiBattle : GameActivity() {
    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        return SeaUjiBattleController(displayMetrics.widthPixels, displayMetrics.heightPixels, applicationContext)
    }
}