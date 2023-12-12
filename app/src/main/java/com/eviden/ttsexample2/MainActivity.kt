package com.eviden.ttsexample2

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eviden.ttsexample2.databinding.ActivityMainBinding
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var textToSpeechRecorder: TextToSpeechRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        textToSpeechRecorder = TextToSpeechRecorder(this)
        textToSpeechRecorder.convertTextToSpeechAndRecord(binding.textView.text.toString())
        val outputFile = textToSpeechRecorder.getOutputFilePath()
        val uri = Uri.fromFile(outputFile)
        mediaPlayer = MediaPlayer.create(this, uri)

        binding.startButton.setOnClickListener {
            mediaPlayer.prepare()
            mediaPlayer.start()
        }

        binding.pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        binding.stopButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare()
            }
        }
    }

    override fun onDestroy() {
        // Release resources
        mediaPlayer.release()
        textToSpeechRecorder.release()
        super.onDestroy()
    }
}

