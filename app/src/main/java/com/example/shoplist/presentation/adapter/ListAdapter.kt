package com.example.shoplist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ListItem
import com.example.shoplist.presentation.ListDiffCallback
import com.example.shoplist.presentation.ListViewHolder

class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {

    var list = listOf<ListItem>(
    )
        set(value) {
            val callback = ListDiffCallback(list, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onListItemClickListener: ((ListItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> R.layout.item_shop_enabled
        }
        val item = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.view.setOnClickListener {
            onListItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_PULL_SIZE = 5
    }
}

