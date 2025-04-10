package com.fauzan0111.fauzan_sleepquality.model

data class SleepCategory(
    val title: String,
    val description: String,
    val tips: List<Pair<Int, Int>> // Pair<TextRes, ImageRes>
)

