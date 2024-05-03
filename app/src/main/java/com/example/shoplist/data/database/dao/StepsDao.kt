package com.example.shoplist.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoplist.data.database.entity.StepsDBModel
import com.example.shoplist.domain.StepItem

@Dao
interface StepsDao {
    @Insert
    fun addItem(stepsDB: StepsDBModel)

    @Query("SELECT * FROM steps_items WHERE id=:itemId LIMIT 1")
    fun getItem(itemId: Int): StepsDBModel

    @Query("SELECT * FROM steps_items")
    fun fetchAllSteps(): LiveData<List<StepsDBModel>>

    @Update
    suspend fun updateItem(item: StepsDBModel)

    @Query("UPDATE steps_items SET state = 1")
    suspend fun resetSteps()

}