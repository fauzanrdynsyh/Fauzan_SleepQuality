package com.fauzan0111.fauzan_sleepquality.nav

sealed class Screen(val route: String) {
    object Input : Screen("input")
    object Result : Screen("result/{sleepHours}") {
        fun createRoute(sleepHours: Float): String = "result/$sleepHours"
    }
}
