package com.fauzan0111.fauzan_sleepquality.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fauzan0111.fauzan_sleepquality.screens.AboutScreen
import com.fauzan0111.fauzan_sleepquality.screens.InfoScreen
import com.fauzan0111.fauzan_sleepquality.screens.InputScreen
import com.fauzan0111.fauzan_sleepquality.screens.MainScreen
import com.fauzan0111.fauzan_sleepquality.screens.ResultScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.Input.route) {
            InputScreen(navController = navController)
        }
        composable(route = Screen.Result.route) { backStackEntry ->
            val sleepHours = backStackEntry.arguments?.getString("sleepHours")?.toFloatOrNull() ?: 0f
            ResultScreen(sleepHours = sleepHours, navController = navController)
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