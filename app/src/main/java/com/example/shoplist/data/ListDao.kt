package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListDao {
    @Query("SELECT * FROM shop_items")
    fun getList(): LiveData<List<ItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(shopItemDBModel: ItemDBModel)

    @Query("DELETE FROM shop_items WHERE id=:itemId")
    suspend fun deleteItem(itemId: Int)

    @Query("SELECT * FROM shop_items WHERE id=:itemId LIMIT 1")
    suspend fun getItem(itemId: Int): ItemDBModel
}