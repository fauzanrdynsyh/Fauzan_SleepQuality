package com.fauzan0111.fauzan_sleepquality.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fauzan0111.fauzan_sleepquality.model.SleepRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepRecordDao {
    @Query("SELECT * FROM sleep_record ORDER BY tanggal DESC")
    fun getAll(): Flow<List<SleepRecord>>

    @Query("SELECT * FROM sleep_record WHERE id = :id")
    fun getById(id: Long): SleepRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sleepQuality: SleepRecord)

    @Update
    fun update(sleepQuality: SleepRecord)

    @Query("DELETE FROM sleep_record WHERE id = :id")
    fun delete(id: Long)
}