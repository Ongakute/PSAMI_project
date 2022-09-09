package com.example.psamiproject

import androidx.recyclerview.widget.DiffUtil
import com.example.psamiproject.data.Point

class RankDiffCallback : DiffUtil.ItemCallback<Point>() {
    override fun areItemsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem == newItem
    }
}