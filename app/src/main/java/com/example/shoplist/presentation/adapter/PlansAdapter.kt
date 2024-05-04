package com.example.shoplist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ListItem
import com.example.shoplist.presentation.ListDiffCallback
import com.example.shoplist.presentation.holder.PlansViewHolder

class PlansAdapter : RecyclerView.Adapter<PlansViewHolder>() {
    var onListItemClickListener: ((ListItem) -> Unit)? = null

    var plansList = listOf<ListItem>()
        set(value) {
            val callback = ListDiffCallback(plansList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder {
        val item: CardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as CardView
        return PlansViewHolder(item)
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        val item = plansList[position]
        holder.bind(item)
        holder.view.setOnClickListener {
            onListItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return plansList.size
    }
}

