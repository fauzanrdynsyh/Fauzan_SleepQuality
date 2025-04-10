package com.fauzan0111.fauzan_sleepquality.nav

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Input : Screen("input")
    object Info : Screen("info")

    object Result : Screen("result/{sleepHours}") {
        fun createRoute(sleepHours: Float): String = "result/$sleepHours"
    }
}

