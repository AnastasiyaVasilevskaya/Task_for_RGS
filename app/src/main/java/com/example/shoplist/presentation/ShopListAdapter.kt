package com.example.shoplist.presentation

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when(viewType){
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val item = LayoutInflater.from(parent.context).inflate(layout,parent, false)
        return ShopListViewHolder(item)
        Log.d(TAG, "onCreateViewHolder")
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.itemName.text = shopItem.name
        holder.itemCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener{
            false
        }
        Log.d(TAG, "onBindViewHolder")
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if(item.enabled){
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    class ShopListViewHolder(val view: View):RecyclerView.ViewHolder(view){
        var itemName = view.findViewById<TextView>(R.id.tv_name)
        val itemCount = view.findViewById<TextView>(R.id.tv_count)
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

        const val MAX_PULL_SIZE = 5
    }
}