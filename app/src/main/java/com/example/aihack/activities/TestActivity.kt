package com.example.aihack.activities

import androidx.fragment.app.Fragment
import com.example.aihack.fragments.TestFragment

class TestActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return TestFragment()
    }
}