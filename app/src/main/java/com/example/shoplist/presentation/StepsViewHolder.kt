package com.example.shoplist.presentation

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.StepItem

class StepsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var itemName = view.findViewById<TextView>(R.id.tv_name)

    fun bind(item: StepItem) {
        itemName.text = item.name
        val searchString = "Повторная проверка на загазованность"
        if (itemName.text.contains(searchString)) {
            val startIndex = itemName.text.indexOf(searchString)

            itemName.text = SpannableString(itemName.text).apply {
                setSpan(
                    ForegroundColorSpan(Color.rgb(211, 29, 20)),
                    startIndex,
                    itemName.text.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}