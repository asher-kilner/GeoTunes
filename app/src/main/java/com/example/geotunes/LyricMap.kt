package com.example.geotunes

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.geotunes.ui.main.SectionsPagerAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

class LyricMap : AppCompatActivity() {
    lateinit var current_song : Song
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyric_map)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        readJson(intent.getStringExtra("song chosen"))
    }

    private fun readJson(filename : String){
        try {
            val json_string = application.assets.open(filename).bufferedReader().use{
                it.readText()
            }
            val gson = GsonBuilder().create()
            val song = gson.fromJson(json_string, Song::class.java)
            current_song = song
            val tidyName = song.name.replace("_", " ")
            val tidyArtist = song.artist.replace("_", " ")
            current_song.name = tidyName
            current_song.artist = tidyArtist
            Log.i("current_song", current_song.name)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
class Song(var artist: String, var name : String, val lyrics : List<String>)

