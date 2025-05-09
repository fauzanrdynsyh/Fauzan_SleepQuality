package com.fauzan0111.fauzan_sleepquality.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fauzan0111.fauzan_sleepquality.screens.AboutScreen
import com.fauzan0111.fauzan_sleepquality.screens.InfoScreen
import com.fauzan0111.fauzan_sleepquality.screens.KEY_ID_DATA
import com.fauzan0111.fauzan_sleepquality.screens.MainScreen
import com.fauzan0111.fauzan_sleepquality.screens.ResultScreen
import com.fauzan0111.fauzan_sleepquality.screens.SleepListScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.TambahDataTidur.route) {
            ResultScreen(navController = navController)
        }
        composable(route = Screen.ListScreen.route) {
            SleepListScreen(navController = navController)
        }
        composable(
            route = Screen.EditDataTidur.route,
            arguments = listOf(navArgument(KEY_ID_DATA) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(KEY_ID_DATA) ?: return@composable
            ResultScreen(id,navController)
        }

        composable(route = Screen.About.route){
            AboutScreen(navController = navController)
        }
        composable(
            route = "info?from={from}",
            arguments = listOf(navArgument("from") {
                defaultValue = "main"
            })
        ) { backStackEntry ->
            val from = backStackEntry.arguments?.getString("from") ?: "main"
            InfoScreen(navController, from)
        }
    }
}