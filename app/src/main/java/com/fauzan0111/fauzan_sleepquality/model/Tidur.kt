package com.fauzan0111.fauzan_sleepquality.model

import com.squareup.moshi.Json

data class Tidur(
    val id: String,
    @Json(name = "waktu_tidur")
    val waktuTidur: String,
    @Json(name = "waktu_bangun")
    val waktuBangun: String,
    val imageId: String,
    val mine: Int
)
