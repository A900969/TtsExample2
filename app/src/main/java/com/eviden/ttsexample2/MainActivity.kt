package com.eviden.ttsexample2

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eviden.ttsexample2.databinding.ActivityMainBinding
import java.io.File
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var textToSpeechRecorder: TextToSpeechRecorder
    private lateinit var file: File
    private lateinit var textToSpeech: TextToSpeech
    private var mAudioFilename = ""
    private var mUtteranceID = "TextToSpeechAudio"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this) {
            textToSpeech.language = Locale.ENGLISH
        }

        binding.startButton.setOnClickListener {
            saveToAudioFile("Esto es un texto de prueba nada más y nada menos")
            createFile()
        }
    }

    fun createFile() {
        file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Tech to Speech Audio")
        if(!file.exists()) {
            val isDirectoryCreated = file.mkdirs()
            if(!isDirectoryCreated) {
                Toast.makeText(this, "Can't create directory to save the Audio", Toast.LENGTH_SHORT).show()
            }
        }
        file.mkdirs()

        mAudioFilename = file.absolutePath + "/" + mUtteranceID + System.currentTimeMillis() + ".wav"
    }

    fun saveToAudioFile(text: String) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.synthesizeToFile(text, null, File(mAudioFilename), mUtteranceID)
        }
        else {
            val hm = HashMap<String, String>()
            hm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, mUtteranceID)
            textToSpeech.synthesizeToFile(text, hm, mAudioFilename)
        }
    }

//        val shareIntent = Intent()
//        shareIntent.action = Intent.ACTION_SEND
//        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content:///" + file.absolutePath))
//        shareIntent.type = "audio/*"
//        startActivity(Intent.createChooser(shareIntent, "Send to"))


      /*  binding.startButton.setOnClickListener {
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
        }*/


    override fun onDestroy() {
        // Release resources
        mediaPlayer.release()
        textToSpeechRecorder.release()
        super.onDestroy()
    }
}

