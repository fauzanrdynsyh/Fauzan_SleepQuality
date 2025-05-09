package com.fauzan0111.fauzan_sleepquality.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan0111.fauzan_sleepquality.database.SleepRecordDao
import com.fauzan0111.fauzan_sleepquality.model.SleepRecord
import com.fauzan0111.fauzan_sleepquality.util.calculateSleepHours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel (private val dao: SleepRecordDao) : ViewModel() {
    fun insert(
        tanggal: String,
        waktuTidur: String,
        waktuBangun: String,
        kualitasTidur: Int,
    ) {
        val durasi = calculateSleepHours(waktuTidur, waktuBangun)
        val record = SleepRecord(
            tanggal = tanggal,
            waktuTidur = waktuTidur,
            waktuBangun = waktuBangun,
            durasiTidur = durasi,
            kualitasTidur = kualitasTidur,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(record)
        }
    }

    fun update(
        id: Long,
        tanggal: String,
        waktuTidur: String,
        waktuBangun: String,
        kualitasTidur: Int,
    ) {
        val record = SleepRecord(
            id = id,
            tanggal = tanggal,
            waktuTidur = waktuTidur,
            waktuBangun = waktuBangun,
            durasiTidur = calculateSleepHours(waktuTidur, waktuBangun),
            kualitasTidur = kualitasTidur,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(record)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(id)
        }
    }

    suspend fun getById(id: Long): SleepRecord? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }
}