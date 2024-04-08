package com.example.shoplist.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoplist.data.database.entity.ItemDBModel
import com.example.shoplist.data.database.dao.ListDao
import com.example.shoplist.data.database.dao.StepsDao
import com.example.shoplist.data.database.entity.StepsDB
import java.io.FileOutputStream
import java.io.IOException

@Database(entities = [ItemDBModel::class, StepsDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stepsDao(): StepsDao
    abstract fun listDao(): ListDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DATA_BASE_NAME = "list_item.db"
        private const val DATA_BASE_STEPS = "steps_items.sql"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application, AppDatabase::class.java, DATA_BASE_NAME
                )
                    .createFromAsset(DATA_BASE_STEPS)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}