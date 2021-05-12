package com.example.a2_seaujibattle.splashActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import com.example.a2_seaujibattle.R
import com.example.a2_seaujibattle.gameActivity.SeaUjiBattle

@SuppressLint("UseSwitchCompatOrMaterialCode")
class MainActivity : AppCompatActivity() {
    lateinit var soundEffectSwitch: Switch
    lateinit var smartOpponetSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.splash_activity)

        soundEffectSwitch = findViewById(R.id.soundEffectSwitch)
        smartOpponetSwitch = findViewById(R.id.smartOpponentSwitch)
    }

    fun startGame(view: View) {
        val intent : Intent = Intent(this, SeaUjiBattle::class.java)
        intent.putExtra("SoundEffects", soundEffectSwitch.isChecked.toString())
        intent.putExtra("SmartOpponent", smartOpponetSwitch.isChecked.toString())
        startActivity(intent)
    }
}