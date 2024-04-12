package com.example.shoplist.presentation

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.StepItem

class StepsListAdapter : RecyclerView.Adapter<ListViewHolder>() {
    var stepsList = listOf<StepItem>()

    var onListItemLongClickListener: ((StepItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val item = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ListViewHolder(item)
        Log.d(ContentValues.TAG, "onCreateViewHolder")
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = stepsList[position]
        holder.itemName.text = item.name
        holder.view.setOnLongClickListener {
            onListItemLongClickListener?.invoke(item)
            false
        }
        Log.d(ContentValues.TAG, "onBindViewHolder")
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = stepsList[position]
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    fun updateList(newList: List<StepItem>) {
        val callback = StepsDiffCallback(stepsList, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        stepsList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
    }
}