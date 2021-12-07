package com.example.aihack.activities

import androidx.fragment.app.Fragment
import com.example.aihack.fragments.MainFragment

class MainActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return MainFragment()
    }

}