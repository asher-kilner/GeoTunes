package com.example.geotunes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var play_button = findViewById<Button>(R.id.play_button)

        play_button.setOnClickListener {
            val intent = Intent(this, LyricMap::class.java)
            startActivity(intent);
        }

    }
}
