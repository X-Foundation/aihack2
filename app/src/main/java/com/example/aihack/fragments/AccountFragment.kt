package com.example.aihack.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.example.aihack.utils.GsonParser
import com.suke.widget.SwitchButton
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
        val xp = GsonParser.getInstance(requireActivity()).getAllXp().toString() + "/83"
        xpTextView.text = xp
        val childImageView = view.findViewById<ImageView>(R.id.child_imageView)
        val adultImageView = view.findViewById<ImageView>(R.id.adult_imageView)
        val switchButton = view.findViewById<SwitchButton>(R.id.switch_button)
        val sharedPref =
            activity?.applicationContext?.getSharedPreferences("superPrefs", Context.MODE_PRIVATE)
        val prefs = sharedPref?.getInt("mode", 0)
        switchButton.isChecked = prefs == 1
        when (prefs == 1) {
            true -> {
                childImageView.visibility = View.VISIBLE
                adultImageView.visibility = View.INVISIBLE
                val editor: SharedPreferences.Editor? = sharedPref?.edit()
                editor?.putInt("mode", 1)
                editor?.apply()
            }
            false -> {
                childImageView.visibility = View.INVISIBLE
                adultImageView.visibility = View.VISIBLE
                val editor: SharedPreferences.Editor? = sharedPref?.edit()
                editor?.putInt("mode", 0)
                editor?.apply()
            }
        }
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    childImageView.visibility = View.VISIBLE
                    adultImageView.visibility = View.INVISIBLE
                    val editor: SharedPreferences.Editor? = sharedPref?.edit()
                    editor?.putInt("mode", 1)
                    editor?.apply()
                }
                false -> {
                    childImageView.visibility = View.INVISIBLE
                    adultImageView.visibility = View.VISIBLE
                    val editor: SharedPreferences.Editor? = sharedPref?.edit()
                    editor?.putInt("mode", 0)
                    editor?.apply()
                }
            }
        }
    }
}