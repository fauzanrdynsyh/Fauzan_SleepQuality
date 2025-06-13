package com.fauzan0111.fauzan_sleepquality.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzan0111.fauzan_sleepquality.model.Tidur
import com.fauzan0111.fauzan_sleepquality.network.TidurApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class GaleriViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Tidur>())
        private set

    var status = MutableStateFlow(TidurApi.ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = TidurApi.ApiStatus.LOADING
            try {
                data.value = TidurApi.service.getTidur(email)
                status.value = TidurApi.ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("GaleriViewModel", "Failure: ${e.message}")
                status.value = TidurApi.ApiStatus.FAILED
            }
        }
    }

    fun saveData(email: String, waktuTidur: String, waktuBangun: String, bitmap: Bitmap){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val result = TidurApi.service.tambahTidur(
                    email,
                    waktuTidur.toRequestBody("text/plain".toMediaTypeOrNull()),
                    waktuBangun.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                if (result.status == "success")
                    retrieveData(email)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("GaleriViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "imageId", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null}
}