package com.fauzan0111.fauzan_sleepquality.util

import java.util.Locale

fun calculateSleepHours(sleepTime: String, wakeTime: String): Float {
    val sleepParts = sleepTime.split(".").mapNotNull { it.toIntOrNull() }
    val wakeParts = wakeTime.split(".").mapNotNull { it.toIntOrNull() }

    if (sleepParts.size < 2 || wakeParts.size < 2) return 0f  // Validasi input

    val sleepMinutes = sleepParts[0] * 60 + sleepParts[1]
    val wakeMinutes = wakeParts[0] * 60 + wakeParts[1]

    var duration = wakeMinutes - sleepMinutes
    if (duration < 0) duration += 24 * 60  // Tambah 24 jam kalau lewat tengah malam

    return String.format(Locale.US, "%.1f", duration / 60f).toFloat()
}