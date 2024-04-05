package com.example.shoplist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var itemName = view.findViewById<TextView>(R.id.tv_name)
}