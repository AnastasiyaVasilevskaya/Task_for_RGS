package com.example.shoplist.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_items")
data class ItemDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)