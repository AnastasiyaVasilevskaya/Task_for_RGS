package com.example.shoplist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ListItem

class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var itemName = view.findViewById<TextView>(R.id.tv_name)

    fun bind(item: ListItem) {
        itemName.text = item.name
    }
}