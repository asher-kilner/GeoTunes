package com.example.geotunes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private var selectMode : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        classic_current_button.setOnClickListener{
            Toast.makeText(this.applicationContext, classic_current_button.isChecked.toString() + " : " + classic_current_button.text, Toast.LENGTH_SHORT).show()
        }

        play_button.setOnClickListener{
            var songChosen = pickRandomSong()
            val intent = Intent(this, LyricMap::class.java)
            intent.putExtra("song chosen", songChosen)
            startActivity(intent)
        }
    }

    // random song from directory
    private fun pickRandomSong(): String {
        if(classic_current_button.isChecked){//current
            var chosenSong = pickRandomIntInAssets("Current")
            return "Current/$chosenSong.txt"
        }
        else{//classic
            var chosenSong = pickRandomIntInAssets("Classic")
            return "Classic/$chosenSong.txt"
        }
    }

    private fun pickRandomIntInAssets(path: String): Int {
        var allClassicSongs = application.assets.list(path)
        var randomSongList = List(1) { Random.nextInt(0, allClassicSongs?.size!!)}
        var randomSongId = randomSongList.get(0);
        return randomSongId
    }


    //on play button pressed




}
