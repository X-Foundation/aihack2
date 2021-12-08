package com.example.aihack.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aihack.R
import com.example.aihack.activities.TrainerActivity
import com.example.aihack.utils.GridSpacingItemDecoration


class LevelFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.level_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.level_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 64, true))
        recyclerView.adapter = RecyclerViewAdapter()

    }
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        private val trainerList = Array(10) {i -> "Уровень ${i+1}"}
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return RecyclerViewHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(trainerList[position])
        }

        override fun getItemCount(): Int {
            return trainerList.size
        }

        inner class RecyclerViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.level_gridview_item, viewGroup, false)){
            fun bind(title: String) {
                val trainerTitle = itemView.findViewById<TextView>(R.id.level_title)
                trainerTitle.text = title
                itemView.setOnClickListener {
                    val intent = Intent(requireActivity(), TrainerActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}