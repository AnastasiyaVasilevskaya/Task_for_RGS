package com.example.shoplist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.shoplist.R
import com.example.shoplist.domain.StepItem
import com.example.shoplist.presentation.StepsDiffCallback
import com.example.shoplist.presentation.holder.StepsViewHolder


class StepsListAdapter : ListAdapter<StepItem, StepsViewHolder>(this) {
    var onListItemLongClickListener: ((StepItem) -> Unit)? = null

    var stepsList = listOf<StepItem>()
        set(value) {
            val callback = StepsDiffCallback(stepsList, value)
            val diffResult = DiffUtil.calculateDiff(callback, false)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        val item: CardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as CardView
        return StepsViewHolder(item)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onListItemLongClickListener)
    }

    companion object : DiffUtil.ItemCallback<StepItem>() {
        override fun areItemsTheSame(oldItem: StepItem, newItem: StepItem): Boolean {
            val same = oldItem == newItem
            return same
        }

        override fun areContentsTheSame(oldItem: StepItem, newItem: StepItem): Boolean {
            return oldItem == newItem
        }
    }
}