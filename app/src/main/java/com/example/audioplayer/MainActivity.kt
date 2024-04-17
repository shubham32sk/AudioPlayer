package com.example.audioplayer

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.PointerIcon
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.filament.View


class MainActivity : AppCompatActivity() {
    lateinit var music: MediaPlayer
    private var currentIndex = 0
    //lateinit var seekbar:SeekBar
    lateinit var vtext:TextView
    private val audiofiles = listOf(R.raw.beat_trap, R.raw.sitar, R.raw.flute)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnstart: Button = findViewById(R.id.btnstart)
        val btnpause: Button = findViewById(R.id.btnpause)
        val btnNext: Button = findViewById(R.id.btnNext)


        vtext=findViewById(R.id.song_name)
        music = MediaPlayer()


        btnstart.setOnClickListener {
            musicstart()
        }
        btnpause.setOnClickListener { musicpause() }
        btnNext.setOnClickListener { nextmusic() }
    }
    fun musicstart() {
        try {
            if(music.isPlaying){
                music.stop()
            }
            val currentAudio = audiofiles[currentIndex]
            val resId = resources.getIdentifier(currentAudio.toString(), "raw", packageName)
            music = MediaPlayer.create(this, resId)
            music.start()
        } catch (e: Exception) {
            Toast.makeText(this, "Error playing audio", Toast.LENGTH_SHORT).show()
        }

    }
    fun musicpause() {
        if(music.isPlaying){
            music.pause()
        }else{
            music.start()
        }
    }

    fun nextmusic() {
        if (currentIndex < audiofiles.size - 1) {
            currentIndex++
        } else {
            currentIndex = 0 // Start from the beginning if at the end of the list
        }
        musicstart()
    }

}
