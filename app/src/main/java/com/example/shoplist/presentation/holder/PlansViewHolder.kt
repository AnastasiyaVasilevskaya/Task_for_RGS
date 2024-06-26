package com.example.shoplist.presentation.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ListItem

class PlansViewHolder(val view: CardView) : RecyclerView.ViewHolder(view) {
    var itemName = view.findViewById<TextView>(R.id.item_name)

    fun bind(item: ListItem) {
        itemName.text = item.name
        itemName.setTextColor(view.context.getColor(R.color.black))
    }
}