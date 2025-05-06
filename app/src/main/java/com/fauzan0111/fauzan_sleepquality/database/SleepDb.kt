package com.fauzan0111.fauzan_sleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fauzan0111.fauzan_sleepquality.model.SleepRecord

@Database(entities = [SleepRecord::class], version = 1)
abstract class SleepDb : RoomDatabase() {
    abstract fun sleepRecordDao() : SleepCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: SleepDb? = null

        fun getDatabase(context: Context): SleepDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SleepDb::class.java,
                    "sleep_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}