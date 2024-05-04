package com.example.shoplist.presentation.holder

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.StepItem

class StepsViewHolder(val view: CardView) : RecyclerView.ViewHolder(view) {

    private var itemName = view.findViewById<TextView>(R.id.item_name)
    private var container = view.findViewById<FrameLayout>(R.id.container)

    fun bind(item: StepItem, onListItemLongClickListener: ((StepItem) -> Unit)?) {
        itemName.text = item.name

        if (item.enabled) {
            itemName.setTextColor(view.context.getColor(R.color.black))
            container.setBackgroundColor(view.context.getColor(R.color.item_background))
        } else {
            itemName.setTextColor(view.context.getColor(R.color.white))
            container.setBackgroundColor(view.context.getColor(R.color.item_background_dis))
        }

        view.setOnLongClickListener {
            onListItemLongClickListener?.invoke(item)
            true
        }

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