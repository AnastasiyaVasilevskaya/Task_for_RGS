package com.example.shoplist.presentation

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ListItem

class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {

    var list = listOf<ListItem>()
        set(value) {
            val callback = ListDiffCallback(list, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onListItemLongClickListener: ((ListItem) -> Unit)? = null
    var onListItemClickListener: ((ListItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val item = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ListViewHolder(item)
        Log.d(TAG, "onCreateViewHolder")
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val items = list[position]
        holder.itemName.text = items.name
        holder.view.setOnLongClickListener {
            onListItemLongClickListener?.invoke(items)
            false
        }
        holder.view.setOnClickListener {
            onListItemClickListener?.invoke(items)
        }
        Log.d(TAG, "onBindViewHolder")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_PULL_SIZE = 5
    }
}