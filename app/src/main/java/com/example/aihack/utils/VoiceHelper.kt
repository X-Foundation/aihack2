package com.example.aihack.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

class VoiceHelper(
    private val owner: FragmentActivity,
    private val onResult: (result: String) -> Unit
) {
    private var speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(owner)
    private var speechRecognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    init {
        ActivityCompat.requestPermissions(
            owner, permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru_RU")
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) {
                val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Toast.makeText(owner, data!![0], Toast.LENGTH_LONG).show()
                onResult(data[0])
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })
    }

    @SuppressLint("RestrictedApi")
    fun startRecording() {
        speechRecognizer.startListening(speechRecognizerIntent)
    }


    companion object {
        private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}