package com.example.psamiproject.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psamiproject.data.UserActivity
import com.example.psamiproject.databinding.ItemActivityBinding

class HistoryAdapter : ListAdapter<UserActivity, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    class ViewHolder(val binding: ItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemActivityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.nameTxt.text = "Ćwiczenie: " + item.name
        holder.binding.countTxt.text = "Ilość: " + item.count
        holder.binding.dateTxt.text = "Data: " + item.stringDate()
    }
}