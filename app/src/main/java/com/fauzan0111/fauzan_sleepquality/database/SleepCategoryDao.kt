package com.fauzan0111.fauzan_sleepquality.database

import androidx.room.Insert
import androidx.room.Query
import com.fauzan0111.fauzan_sleepquality.model.SleepRecord

interface SleepCategoryDao {
    @Insert
    suspend fun insertSleepRecord(sleepRecord: SleepRecord)

    @Query("SELECT * FROM sleep_record")
    suspend fun getAllSleepRecords(): List<SleepRecord>

    @Query("DELETE FROM sleep_record")
    suspend fun deleteAllSleepRecords()
}