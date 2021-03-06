package com.example.aihack.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

class VoiceHelper(
    owner: FragmentActivity,
    private val onResult: (result: String) -> Unit
) {
    private var speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(owner)
    private var speechRecognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    init {
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
            override fun onError(i: Int) {
                if(FaceContourGraphic.MOUTH_OPENED){
                    Toast.makeText(owner, "Вас не слышно", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(owner, "Попробуйте открыть рот", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onResults(bundle: Bundle) {
                val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                onResult(data!![0])
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })
    }

    @SuppressLint("RestrictedApi")
    fun startRecording() {
        speechRecognizer.startListening(speechRecognizerIntent)
    }
}