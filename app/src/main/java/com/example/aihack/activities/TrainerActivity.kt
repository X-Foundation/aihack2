package com.example.aihack.activities

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.aihack.fragments.TrainerFragment

class TrainerActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return TrainerFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}