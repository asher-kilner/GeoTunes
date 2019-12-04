package com.example.geotunes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.geotunes.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder


class LyricMap : AppCompatActivity() {
    lateinit var current_song : Song
    lateinit var viewPager : ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyric_map)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
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

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCurrentFragment(item: Int, smoothScroll: Boolean) {
        viewPager.setCurrentItem(item, smoothScroll)
    }
}
class Song(var artist: String, var name : String, val lyrics : List<String>)

