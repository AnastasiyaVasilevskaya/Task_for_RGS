package com.example.shoplist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoplist.data.database.entity.StepsDB

@Dao
interface StepsDao {

    @Insert
    fun addName(stepsDB: StepsDB)

    @Query("SELECT * FROM steps_items")
    fun fetchAllSteps(): List<StepsDB>

}