package com.example.aihack.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.example.aihack.utils.GsonParser
import params.com.stepprogressview.StepProgressView

class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val level = "Уровень " + GsonParser.getInstance(requireActivity()).getLevel()
        val levelTextView = view.findViewById<TextView>(R.id.level_textView)
        val xpProgressBar = view.findViewById<StepProgressView>(R.id.xp_progressBar)
        levelTextView.text = level
        xpProgressBar.currentProgress = GsonParser.getInstance(requireActivity()).getAllXp()
        val xpTextView = view.findViewById<TextView>(R.id.xp_textView)
        val xp = GsonParser.getInstance(requireActivity()).getAllXp().toString() + "/81"
        xpTextView.text = xp
    }
}