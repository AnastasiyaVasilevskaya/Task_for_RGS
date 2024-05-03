package com.example.shoplist.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoplist.data.ListDBMigration
import com.example.shoplist.data.database.entity.ItemDBModel
import com.example.shoplist.data.database.dao.ListDao
import com.example.shoplist.data.database.dao.StepsDao
import com.example.shoplist.data.database.entity.StepsDBModel

@Database(entities = [ItemDBModel::class, StepsDBModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun stepsDao(): StepsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DATABASE_NAME = "plans_and_steps_item.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application, AppDatabase::class.java, DATABASE_NAME
                )
                    .addCallback(ListDBMigration())
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}