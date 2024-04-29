package com.example.audioplayer

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.PointerIcon
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.google.ads.interactivemedia.pal.utils.Duration
import com.google.android.filament.View
import com.google.android.material.slider.RangeSlider
import kotlin.time.toDuration


class MainActivity : AppCompatActivity() {
    lateinit var music: MediaPlayer
    private var currentIndex = 0
    lateinit var seekBar: SeekBar
    lateinit var vtext: EditText
    private val song= listOf("Beat_trap","Sitar","Flute")
    private val audiofiles = listOf(R.raw.beat_trap, R.raw.sitar, R.raw.flute)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart: Button = findViewById(R.id.btnstart)
        val btnPause: Button = findViewById(R.id.btnpause)
        val btnNext: Button = findViewById(R.id.btnNext)
        seekBar = findViewById(R.id.seekBar)


        vtext=findViewById(R.id.title)
        music = MediaPlayer()

        //setupseekbar
        setUpSeekBar()



        btnStart.setOnClickListener {
            musicstart()

        }
        btnPause.setOnClickListener { musicpause() }
        btnNext.setOnClickListener { nextmusic() }





    }

    private fun setdata() {
        vtext.setText(song[currentIndex])

    }

    private fun setUpSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    music?.seekTo(progress)}
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                music?.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                music?.start()
            }
        }) }
    private val seekBarUpdateHandler=Handler()
    private val seekBarUpdateRunnable=object :Runnable{
        override fun run() {
            music?.let {
                seekBar.progress = it.currentPosition
                seekBarUpdateHandler.postDelayed(this, 1000)
            }
        }
    }

    private fun startUpdatingSeekBar() {
        seekBarUpdateHandler.post(seekBarUpdateRunnable)
    }


    fun musicstart() {
        try {
            if (music.isPlaying) {
                music.stop()
            }
            val currentAudio = audiofiles[currentIndex]
            val resId = resources.getIdentifier(currentAudio.toString(), "raw", packageName)
            music = MediaPlayer.create(this, resId)
            music.start()
            startUpdatingSeekBar()
            seekBar.max=music!!.duration
            setdata()
        } catch (e: Exception) {
            Toast.makeText(this, "Error playing audio", Toast.LENGTH_SHORT).show()
        }

    }


    fun musicpause() {
        if (music.isPlaying) {
            music.pause()
        } else {
            music.start()
            seekBar.max=music!!.duration
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


