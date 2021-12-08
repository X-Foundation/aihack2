package com.example.aihack.activities

import androidx.fragment.app.Fragment
import com.example.aihack.fragments.TrainerFragment

class TrainerActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return TrainerFragment()
    }
}