package com.example.a2_seaujibattle.splashActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
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
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.splash_activity)

        soundEffectSwitch = findViewById(R.id.soundEffectSwitch)
        smartOpponetSwitch = findViewById(R.id.smartOpponentSwitch)

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.pirates)
        mediaPlayer.setVolume(0.35F, 0.35F)
    }

    fun startGame(view: View) {
        if (mediaPlayer.isPlaying)
            mediaPlayer.release()
        val intent : Intent = Intent(this, SeaUjiBattle::class.java)
        intent.putExtra("SoundEffects", soundEffectSwitch.isChecked.toString())
        intent.putExtra("SmartOpponent", smartOpponetSwitch.isChecked.toString())
        startActivity(intent)
    }

    fun turnOnOffMusic(view: View) {
        if (soundEffectSwitch.isChecked) {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.pirates)
            mediaPlayer.setVolume(0.35F, 0.35F)
            mediaPlayer.start()
        }

        else
            if (mediaPlayer.isPlaying)
                mediaPlayer.release()
    }
}