package com.fauzan0111.fauzan_sleepquality.network

import com.fauzan0111.fauzan_sleepquality.model.Tidur
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://sleep-api.bagasaldianata.my.id/api/"

private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TidurApiService {
    @GET("sleep")
    suspend fun getTidur() : List<Tidur>
}

object TidurApi {
    val service: TidurApiService by lazy {
        retrofit.create(TidurApiService::class.java)
    }

    fun getTidurUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }

    enum class ApiStatus { LOADING, SUCCESS, FAILED}
}