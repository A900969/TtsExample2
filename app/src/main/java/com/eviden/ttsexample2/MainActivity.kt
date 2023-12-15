package com.eviden.ttsexample2

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eviden.ttsexample2.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object {
        const val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1
    }

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var textToSpeechRecorder: TextToSpeechRecorder
    private lateinit var file: File
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        file = File(this.getExternalFilesDir(null), "audio.mp3")

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Toast.makeText(this, "TTS init", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No se pudo inicializar tts.", Toast.LENGTH_LONG).show()
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.synthesizeToFile("Esto es un texto de prueba", null, file, null)
        }
        else {
            textToSpeech.synthesizeToFile("Esto es un texto de prueba", null, file.absolutePath)
        }

//        val shareIntent = Intent()
//        shareIntent.action = Intent.ACTION_SEND
//        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content:///" + file.absolutePath))
//        shareIntent.type = "audio/*"
//        startActivity(Intent.createChooser(shareIntent, "Send to"))


        binding.startButton.setOnClickListener {
            try{
                val uri = Uri.parse("content:///" + file.absolutePath)
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

