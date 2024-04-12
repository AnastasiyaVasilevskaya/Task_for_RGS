package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ListRepository {
    fun getList(): LiveData<List<ListItem>>
    suspend fun addItem(item: ListItem)
    suspend fun getItem(itemId: Int): ListItem
}