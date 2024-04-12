package com.example.shoplist.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps_items")
data class StepsDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "state") val enabled: Boolean
)