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
import android.app.AlertDialog
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_guess.*

class LyricActivity : Fragment(){
    lateinit var parent_activity : Activity
    lateinit var song : Song
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_lyrics,container,false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_button.setOnClickListener{startActivity(Intent(activity, MainActivity::class.java))}
        guess_button.setOnClickListener{makeGuess()}
        displayLyrics()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        getSongFromParent()
    }

    private fun  getSongFromParent(){

        try {
            parent_activity = activity as LyricMap
            song = (parent_activity as LyricMap).current_song
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnHeadlineSelectedListener")
        }
    }

    private fun displayLyrics(){
        var lyricString : String = ""
        for (line in song.lyrics){
            lyricString += line + "\n"
        }
        lyrics.text = lyricString
    }

    fun makeGuess(){

        val mBuilder = AlertDialog.Builder(this.context)
        val mView  = layoutInflater.inflate(R.layout.dialog_guess,null)
        val mArtist = mView.findViewById(R.id.etArtist) as EditText
        val mSong = mView.findViewById(R.id.etSong) as EditText
        val mGuess = mView.findViewById(R.id.make_guess_button) as Button
        val mGiveUp = mView.findViewById(R.id.give_up_button) as Button
        var mCorrect: Boolean

        mBuilder.setView(mView)
        var dialog = mBuilder.create() as AlertDialog
        dialog.show()

        mGuess.setOnClickListener {
            //if(!mArtist.text.toString().isEmpty() && !mSong.text.toString().isEmpty()){
            if(true){
                mCorrect = isGuessCorrect(mArtist.text.toString(), mSong.text.toString())
                if(mCorrect){
                    //Toast.makeText(this.context, getString(R.string.correct_guess_made),Toast.LENGTH_SHORT).show()
                    displayCongratulations()
                }else{
                    //Toast.makeText(this.context, "not quite, keep playing to get more clues or give up",Toast.LENGTH_SHORT).show()
                    displayComiserations()
                    dialog.dismiss()
                }

            }else{
                Toast.makeText(this.context, getString(R.string.no_input_given),Toast.LENGTH_SHORT).show()
            }
        }

        mGiveUp.setOnClickListener{
            AlertDialog.Builder(this.context)
                .setTitle("Give up")
                .setMessage("are you sure you want to give up?\n you will recieve no points.")
                .setPositiveButton(getString(R.string.give_up)){ dialog, which ->
                    Toast.makeText(this.context, getString(R.string.give_up),Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton(getString(R.string.keep_playing)) { dialog, which ->
                    Toast.makeText(this.context, getString(R.string.keep_playing), Toast.LENGTH_SHORT).show()
                }
                .show()
        }
        //options if it is wrong
    }

    private fun displayComiserations() {
        val mBuilder = AlertDialog.Builder(this.context)
        val mView  = layoutInflater.inflate(R.layout.commiserations,null)
        val mHome = mView.findViewById(R.id.home_button) as Button
        val mPlay = mView.findViewById(R.id.keep_playing_button) as Button

        mBuilder.setView(mView)
        var dialog = mBuilder.create() as AlertDialog
        dialog.show()

        mHome.setOnClickListener{
            startActivity(Intent(activity, MainActivity::class.java))
        }
        mPlay.setOnClickListener{
            dialog.dismiss()
        }
    }

    private fun displayCongratulations() {
        var mBuilder = AlertDialog.Builder(this.context)
        var mView  = layoutInflater.inflate(R.layout.congratulations,null)
        var mHome = mView.findViewById(R.id.home_button) as Button
        var mArtist = mView.findViewById(R.id.artist_txt) as TextView
        var mSong = mView.findViewById(R.id.song_txt) as TextView

        mArtist.text = song.artist
        mSong.text = song.name

        mBuilder.setView(mView)
        var dialog = mBuilder.create() as AlertDialog
        dialog.show()

        mHome.setOnClickListener{
            startActivity(Intent(activity, MainActivity::class.java))
        }

    }

    private fun isGuessCorrect(artist: String, name: String): Boolean {

        Toast.makeText(this.context, song.artist + " " + song.name , Toast.LENGTH_LONG).show()
        if(name.toUpperCase().trimEnd() == song.name.toUpperCase()){
            if(artist.toUpperCase().trimEnd() == song.artist.toUpperCase()){
                return true
            }
        }
        return false
    }
}