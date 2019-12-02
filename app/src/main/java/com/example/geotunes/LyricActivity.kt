package com.example.geotunes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lyrics.*
import android.app.Activity
import android.util.Log


@Suppress("DEPRECATION")
class LyricActivity : Fragment(){
    lateinit var parent_activity : Activity
    lateinit var song : Song
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_lyrics,container,false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("current_song", song.name)
        home_button.setOnClickListener{
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
        }
        var lyricString : String = ""
        for (line in song?.lyrics!!){
            lyricString += line + "\n"
        }
        lyrics.text = lyricString
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            parent_activity = activity as LyricMap
            song = (parent_activity as LyricMap).current_song
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnHeadlineSelectedListener")
        }

    }
}