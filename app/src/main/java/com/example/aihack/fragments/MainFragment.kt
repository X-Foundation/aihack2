package com.example.aihack.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.account -> {
                    setFragment(AccountFragment())
                    true
                }
                R.id.trainer -> {
                    setFragment(TrainerFragment())
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.account
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, fragment)
        }.commit()
    }
}