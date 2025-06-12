package com.fauzan0111.fauzan_sleepquality.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan0111.fauzan_sleepquality.model.Tidur
import com.fauzan0111.fauzan_sleepquality.network.TidurApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class GaleriViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Tidur>())
        private set

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = TidurApi.service.getTidur()
            } catch (e: Exception) {
                Log.d("GaleriViewModel", "Failure: ${e.message}")
            }
        }
    }
}