package com.example.aihack.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.aihack.R


class LevelFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.level_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridview = view.findViewById<GridView>(R.id.gridview)
        val levels = Array(9) { i -> "Уровень ${i + 1}" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, levels)
        gridview?.adapter = adapter;
        val itemListener =
            AdapterView.OnItemClickListener() { _: AdapterView<*>, _: View, _: Int, _: Long ->
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, TrainerFragment())
                }.commit()
            }
        gridview?.onItemClickListener = itemListener;
    }

    companion object
}