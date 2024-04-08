package com.example.shoplist.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoplist.data.database.entity.ItemDBModel

@Dao
interface ListDao {
    @Query("SELECT * FROM list_items")
    fun getList(): LiveData<List<ItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(shopItemDBModel: ItemDBModel)

    @Query("DELETE FROM list_items WHERE id=:itemId")
    suspend fun deleteItem(itemId: Int)

    @Query("SELECT * FROM list_items WHERE id=:itemId LIMIT 1")
    suspend fun getItem(itemId: Int): ItemDBModel
}