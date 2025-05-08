package com.fauzan0111.fauzan_sleepquality.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fauzan0111.fauzan_sleepquality.database.SleepDb
import com.fauzan0111.fauzan_sleepquality.screens.ResultViewModel
import com.fauzan0111.fauzan_sleepquality.screens.SleepViewModel

class SleepViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = SleepDb.getDatabase(context).sleepRecordDao()
        if (modelClass.isAssignableFrom(SleepViewModel::class.java)){
            return SleepViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(ResultViewModel::class.java)){
            return ResultViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}