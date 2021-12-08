package com.example.aihack.activities

import androidx.fragment.app.Fragment
import com.example.aihack.fragments.MainFragment
import com.example.aihack.utils.GsonParser

class MainActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return MainFragment()
    }

}