package com.eviden.ttsexample2

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale


class TextToSpeechRecorder(context: Context?) {
    private val textToSpeech: TextToSpeech?
    private var mediaRecorder: MediaRecorder? = null
    private val outputFile: File

    init {
        var create = false
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                create = true
            } else {
                Toast.makeText(context, "No se pudo inicializar tts.", Toast.LENGTH_LONG).show()
            }
        }
        if (create) textToSpeech.language = Locale.getDefault()
        // Output file path
        outputFile = File(context?.getExternalFilesDir(null), "audio.wav")
    }


    fun convertTextToSpeechAndRecord(text: String?) {
        textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onStart(utteranceId: String) {
                startRecording()
            }

            override fun onDone(utteranceId: String) {
                stopRecording()
            }

            override fun onError(utteranceId: String) {
                Log.d("JRB", "Error on record")
            }
        })

        // Record params config
        val params = bundleOf(
            TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID to "UtteranceId",
            TextToSpeech.Engine.KEY_PARAM_VOLUME to 0.8f //volume adjust
        )

        textToSpeech?.synthesizeToFile(text, params, outputFile, "UtteranceId")
        Log.d("JRB", "Error on record")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun startRecording() {

        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder!!.setOutputFile(outputFile.absolutePath)
        try {
            mediaRecorder!!.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaRecorder!!.start()
    }


    private fun stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder!!.stop()
            mediaRecorder!!.release()
            mediaRecorder = null
        }
    }


    fun release() {
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    fun getOutputFile(): File {
        return outputFile
    }
}
