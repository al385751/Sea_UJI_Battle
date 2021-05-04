package com.example.a2_seaujibattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "UJI Sea Battle"
    }

    fun startGame(view: View) {
        val intent : Intent = Intent(this, SeaUjiBattle::class.java)
        startActivity(intent)
    }
}