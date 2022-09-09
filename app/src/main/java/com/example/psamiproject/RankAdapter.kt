package com.example.psamiproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psamiproject.data.Point
import com.example.psamiproject.databinding.ActivityRankBinding
import com.example.psamiproject.databinding.ItemActivityBinding
import com.example.psamiproject.databinding.ItemLeaderboardBinding

class RankAdapter : ListAdapter<Point, RankAdapter.ViewHolder>(RankDiffCallback()) {

    class ViewHolder(val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLeaderboardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.nameTxt.text = "Użytkownik: " + item.username
        holder.binding.pointsTxt.text = "Ilość punktów: " + item.value
    }
}