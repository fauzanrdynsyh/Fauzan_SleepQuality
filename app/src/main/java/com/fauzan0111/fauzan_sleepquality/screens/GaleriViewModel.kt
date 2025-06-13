package com.fauzan0111.fauzan_sleepquality.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan0111.fauzan_sleepquality.model.Tidur
import com.fauzan0111.fauzan_sleepquality.network.TidurApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GaleriViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Tidur>())
        private set

    var status = MutableStateFlow(TidurApi.ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = TidurApi.ApiStatus.LOADING
            try {
                data.value = TidurApi.service.getTidur()
                status.value = TidurApi.ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("GaleriViewModel", "Failure: ${e.message}")
                status.value = TidurApi.ApiStatus.FAILED
            }
        }
    }
}