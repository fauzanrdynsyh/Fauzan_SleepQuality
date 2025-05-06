package com.fauzan0111.fauzan_sleepquality.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_record")
data class SleepRecord (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sleepTime: String,
    val wakeTime: String,
    val duration: Float
)

