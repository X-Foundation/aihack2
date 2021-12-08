package com.example.aihack.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.example.aihack.utils.CameraHelper
import com.example.aihack.utils.VoiceHelper
import android.text.style.ForegroundColorSpan

import android.text.SpannableString

import android.text.SpannableStringBuilder


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

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.textView2)
        textView.text = SENTENCE
        val button = view.findViewById<Button>(R.id.button)
        voiceHelper = VoiceHelper(requireActivity(), onResult = {
            val recognized = it.lowercase().replace('с', 'c').replace('c', 'с')
            val split1 = recognized.split(' ')
            val splitRight = SENTENCE.split(' ')
            val builder = SpannableStringBuilder()
            for ((idx, i) in splitRight.withIndex()) {
                var text = i
                if (idx != splitRight.size - 1)
                    text = "$i "
                val str1 = SpannableString(text)
                if (i in split1) {
                    str1.setSpan(ForegroundColorSpan(Color.GREEN), 0, str1.length, 0)
                } else {
                    str1.setSpan(ForegroundColorSpan(Color.RED), 0, str1.length, 0)
                }
                builder.append(str1)
            }
            textView.setText(builder, TextView.BufferType.SPANNABLE)
        })
        button.setOnClickListener {
            voiceHelper.startRecording()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        cameraHelper.onRequestPermissionsResult(requestCode)
    }

    companion object {
        const val TAG = "CameraXDemo"
        const val SENTENCE = "какая сегодня хорошая погода"
    }
}