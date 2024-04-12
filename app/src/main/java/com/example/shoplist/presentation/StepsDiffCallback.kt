package com.example.shoplist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoplist.domain.StepItem

class StepsDiffCallback(
    private val oldList: List<StepItem>,
    private val newList: List<StepItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}