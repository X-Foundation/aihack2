package com.example.aihack.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.example.aihack.utils.CameraHelper
import java.util.*


class TestFragment : Fragment() {
    private lateinit var cameraHelper: CameraHelper
    private lateinit var safeContext: Context
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechRecognizerIntent: Intent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button)
        val button2 = view.findViewById<Button>(R.id.button2)
        button.setOnClickListener{
            startRecording()
        }
        button2.setOnClickListener{
            stopRecording()
        }

        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        cameraHelper = CameraHelper(
            owner = requireActivity(),
            context = safeContext,
            viewFinder = view.findViewById(R.id.cameraView),
            overlay = view.findViewById(R.id.graphicOverlay_finder)
        )
        cameraHelper.start()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity())
        speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("ru_RU"))
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) {
                val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Toast.makeText(requireActivity(), data!![0], Toast.LENGTH_LONG).show()
            }
            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraHelper.stop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        cameraHelper.onRequestPermissionsResult(requestCode)
    }
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    @SuppressLint("RestrictedApi")
    private fun startRecording() {
        speechRecognizer.startListening(speechRecognizerIntent)
    }

    private fun stopRecording() {
        speechRecognizer.stopListening()
    }

    companion object {
        const val TAG = "CameraXDemo"
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}