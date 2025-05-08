package com.fauzan0111.fauzan_sleepquality.nav

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object TambahDataTidur : Screen("tambah")
    data object Info : Screen("info")
    data object About : Screen("about")
    object EditDataTidur : Screen("edit/{id}") {
        fun withId(id: Long): String {
            return "edit/$id"
        }
    }
}

