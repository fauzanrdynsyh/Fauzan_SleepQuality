package com.fauzan0111.fauzan_sleepquality.network

import com.fauzan0111.fauzan_sleepquality.model.OpStatus
import com.fauzan0111.fauzan_sleepquality.model.Tidur
import com.squareup.moshi.Moshi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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
    suspend fun getTidur(
        @Header("Authorization") email: String
    ) : List<Tidur>

    @Multipart
    @POST("sleep")
    suspend fun tambahTidur(
        @Header("Authorization") email: String,
        @Part("waktu_tidur") waktuTidur: RequestBody,
        @Part("waktu_bangun") waktuBangun: RequestBody,
        @Part imageId: MultipartBody.Part
    ): OpStatus

    @DELETE("sleep")
    suspend fun hapusTidur(
        @Header("Authorization") email: String,
        @Query("id") id: String
    ): OpStatus

    @Multipart
    @POST("sleep")
    suspend fun updateTidur(
        @Header("Authorization") email: String,
        @Query("id") id: String,
        @Part("_method") method: RequestBody,
        @Part("waktu_tidur") waktuTidur: RequestBody,
        @Part("waktu_bangun") waktuBangun: RequestBody,
        @Part imageId: MultipartBody.Part?
    ): Tidur

}

object TidurApi {
    val service: TidurApiService by lazy {
        retrofit.create(TidurApiService::class.java)
    }

    fun getTidurUrl(imageId: String): String {
        return "${BASE_URL}image?id=$imageId"
    }

    enum class ApiStatus { LOADING, SUCCESS, FAILED}
}