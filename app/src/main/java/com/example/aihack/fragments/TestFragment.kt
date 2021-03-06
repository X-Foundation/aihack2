package com.example.aihack.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.example.aihack.utils.CameraHelper
import com.example.aihack.utils.FaceContourGraphic
import com.example.aihack.utils.GsonParser
import com.example.aihack.utils.VoiceHelper
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.*


class TestFragment : Fragment() {
    private lateinit var cameraHelper: CameraHelper
    private lateinit var voiceHelper: VoiceHelper
    private lateinit var safeContext: Context

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

    private var textToSpeechSystem: TextToSpeech? = null

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        FaceContourGraphic.MOUTH_OPENED = false
        val textView = view.findViewById<TextView>(R.id.textView2)
        val level = requireActivity().intent.getIntExtra("level", 0)
        val testNumber = requireActivity().intent.getIntExtra("test", 0)
        val testText = GsonParser.getInstance(requireActivity()).getTest(level, testNumber)
        textView.text = testText
        val button = view.findViewById<Button>(R.id.button)
        val button2 = view.findViewById<Button>(R.id.button2)
        voiceHelper = VoiceHelper(requireActivity(), onResult = {
            val recognized = it.lowercase().replace('??', 'c').replace('c', '??')
            val split1 = recognized.split(' ')
            val splitRight = testText!!.split(' ')
            val builder = SpannableStringBuilder()
            var counter = 0
            for ((idx, i) in splitRight.withIndex()) {
                var text = i
                if (idx != splitRight.size - 1)
                    text = "$i "
                val str1 = SpannableString(text)
                if (i in split1) {
                    counter++
                    str1.setSpan(ForegroundColorSpan(Color.GREEN), 0, str1.length, 0)
                } else {
                    str1.setSpan(ForegroundColorSpan(Color.RED), 0, str1.length, 0)
                }
                builder.append(str1)
            }
            textView.setText(builder, TextView.BufferType.SPANNABLE)
            if (counter == splitRight.size) {
                val konfettiView = view.findViewById<KonfettiView>(R.id.viewKonfetti)
                konfettiView.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.Square, Shape.Circle)
                    .addSizes(Size(12))
                    .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                    .streamFor(300, 5000L)
            }
            GsonParser.getInstance(requireActivity()).addXp(level, testNumber, counter, requireActivity())
        })
        button.setOnClickListener {
            voiceHelper.startRecording()
        }
        button2.setOnClickListener {
            textToSpeechSystem = TextToSpeech(context
            ) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechSystem?.language = Locale("RU_ru")
                    textToSpeechSystem?.speak(testText, TextToSpeech.QUEUE_ADD, null)
                }
            }
        }
        cameraHelper = CameraHelper(
            owner = requireActivity(),
            context = safeContext,
            viewFinder = view.findViewById(R.id.cameraView),
            overlay = view.findViewById(R.id.graphicOverlay_finder)
        )
        cameraHelper.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraHelper.stop()
    }

    companion object {
        const val TAG = "CameraXDemo"
    }
}