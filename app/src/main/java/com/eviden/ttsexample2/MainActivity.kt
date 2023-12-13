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

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var textToSpeechRecorder: TextToSpeechRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        textToSpeechRecorder = TextToSpeechRecorder(this)
        textToSpeechRecorder.convertTextToSpeechAndRecord(binding.textView.text.toString())
        val outputFile = textToSpeechRecorder.getOutputFile()
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio)


        binding.startButton.setOnClickListener {
            try{
                val uri = Uri.parse("file:///"+outputFile.absolutePath)
                mediaPlayer.setDataSource(this, uri)
                mediaPlayer.prepare()
                mediaPlayer.start()
                binding.stopButton.isEnabled = true
                binding.pauseButton.isEnabled = true
            }catch (e: IOException) {
                e.printStackTrace()
            }
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
                binding.stopButton.isEnabled = false
                binding.pauseButton.isEnabled = false
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

