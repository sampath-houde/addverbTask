package com.example.addverbtask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.addverbtask.data.RegionResponse
import com.example.addverbtask.database.dao.RegionDao
import com.example.addverbtask.database.entities.RegionOffline
import com.task.krishinetwork.utils.Constants

@Database(entities = [RegionOffline::class], version = 1, exportSchema = false )
abstract class RegionDatabase: RoomDatabase() {
    abstract fun userDao(): RegionDao

    companion object {
        @Volatile
        private var INSTANCE: RegionDatabase? = null

        fun getDatabase(context: Context): RegionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RegionDatabase::class.java,
                    Constants.TABLE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}