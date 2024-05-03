package com.example.shoplist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.StepItem
import com.example.shoplist.presentation.StepsDiffCallback
import com.example.shoplist.presentation.StepsViewHolder

class StepsListAdapter : RecyclerView.Adapter<StepsViewHolder>() {
    var stepsList = listOf<StepItem>()
        set(value) {
            val callback = StepsDiffCallback(stepsList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onListItemLongClickListener: ((StepItem) -> Unit)? = null
    var onResetButtonClickListener: ((List<StepItem>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val item = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return StepsViewHolder(item)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        val item = stepsList[position]
        holder.bind(item)
        holder.itemName.setOnLongClickListener {
            onListItemLongClickListener?.invoke(item)
            true
        }

//        resetButton.setOnClickListener {
//            onResetButtonClickListener?.invoke(stepsList)
//            true
//        }
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



    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
    }
}