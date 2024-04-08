package com.example.shoplist.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps_items")
data class StepsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val enabled: Int
)