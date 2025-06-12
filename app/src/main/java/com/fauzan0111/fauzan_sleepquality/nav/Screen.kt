package com.fauzan0111.fauzan_sleepquality.nav

import com.fauzan0111.fauzan_sleepquality.screens.KEY_ID_DATA

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object ListScreen : Screen ("list")
    data object TambahDataTidur : Screen("edit")
    data object Info : Screen("info")
    data object About : Screen("about")
    data object GaleriTidur: Screen("galeri")
    data object EditDataTidur : Screen("edit/{$KEY_ID_DATA}") {
        fun withId(id: Long): String {
            return "edit/$id"
        }
    }
}

