package com.example.aihack.activities

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.aihack.fragments.TestFragment

class TestActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return TestFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}