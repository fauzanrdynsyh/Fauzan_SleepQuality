package com.fauzan0111.fauzan_sleepquality.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_record")
data class SleepRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tanggal: String,
    val waktuTidur: String,
    val waktuBangun: String,
    val durasiTidur: Float,
    val kualitasTidur: Int,
)

