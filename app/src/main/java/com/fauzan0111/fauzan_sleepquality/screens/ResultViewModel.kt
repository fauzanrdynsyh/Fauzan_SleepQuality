package com.fauzan0111.fauzan_sleepquality.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan0111.fauzan_sleepquality.database.SleepRecordDao
import com.fauzan0111.fauzan_sleepquality.model.SleepRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class ResultViewModel (private val dao: SleepRecordDao) : ViewModel() {
    fun insert(
        tanggal: String,
        waktuTidur: String,
        waktuBangun: String,
        kualitasTidur: Int,
    ) {
        val durasi = calculateDuration(waktuTidur, waktuBangun)
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
            durasiTidur = calculateDuration(waktuTidur, waktuBangun),
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


    private fun calculateDuration(waktuTidur: String, waktuBangun: String): Float {
        try {
            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

            val tidurTime = formatter.parse(waktuTidur) ?: return 0f
            val bangunTime = formatter.parse(waktuBangun) ?: return 0f

            var durationMillis = bangunTime.time - tidurTime.time

            if (durationMillis < 0) {
                durationMillis += 24 * 60 * 60 * 1000 // Tambahkan 24 jam
            }

            return durationMillis / (1000f * 60f * 60f)
        } catch (e: Exception) {
            e.printStackTrace()
            return 0f
        }

    }
}