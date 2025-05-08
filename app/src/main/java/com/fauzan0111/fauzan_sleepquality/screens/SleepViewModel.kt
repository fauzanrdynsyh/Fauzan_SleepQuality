package com.fauzan0111.fauzan_sleepquality.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan0111.fauzan_sleepquality.database.SleepRecordDao
import com.fauzan0111.fauzan_sleepquality.model.SleepRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class SleepViewModel(private val dao: SleepRecordDao) : ViewModel() {
    val data: StateFlow<List<SleepRecord>> = dao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}