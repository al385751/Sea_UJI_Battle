package com.example.a2_seaujibattle.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.a2_seaujibattle.R
import com.example.a2_seaujibattle.gameActivity.SeaUjiBattle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }

    fun startGame(view: View) {
        val intent : Intent = Intent(this, SeaUjiBattle::class.java)
        startActivity(intent)
    }
}