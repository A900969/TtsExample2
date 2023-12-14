package com.eviden.ttsexample2

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import com.eviden.ttsexample2.util.Callback
import com.eviden.ttsexample2.util.ProgressState
import java.io.File
import java.util.UUID



class TTS(private val context: Context) {
    private val tts: TextToSpeech
    private var onInitCallback: Callback<Int>? = null
    private var onPlayCallback: Callback<ProgressState>? = null

    init {
        tts = TextToSpeech(context) { status ->
            onInitCallback?.onDone(status)
        }
    }

    fun setOnPlayCallback(onPlayCallback: Callback<ProgressState>?) {
        this.onPlayCallback = onPlayCallback
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(s: String) {
                onPlayCallback?.onDone(ProgressState.START)
            }

            override fun onDone(s: String) {
                onPlayCallback?.onDone(ProgressState.STOP)
            }

            override fun onError(s: String) {}
        })
    }

    fun setOnInitCallback(onInitCallback: Callback<Int>?) {
        this.onInitCallback = onInitCallback
    }

    val voices: Set<Voice>
        get() = tts.voices

    fun speak(text: String?) {
        if (tts.isSpeaking) {
            tts.stop()
            onPlayCallback?.onDone(ProgressState.STOP)
            return
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "1")
    }

    @Throws(Exception::class)
    fun speakToBuffer(text: String?): File {
        val file = File(context.cacheDir, UUID.randomUUID().toString() + ".wav")
        val res = tts.synthesizeToFile(text, null, file, null)
        if (res != TextToSpeech.SUCCESS) throw Exception("synth error")
        return file
    }
}