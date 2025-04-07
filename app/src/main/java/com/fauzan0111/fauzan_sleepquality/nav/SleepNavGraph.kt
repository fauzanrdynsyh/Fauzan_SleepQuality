package com.fauzan0111.fauzan_sleepquality.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fauzan0111.fauzan_sleepquality.screens.InputScreen
import com.fauzan0111.fauzan_sleepquality.screens.ResultScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Input.route
    ) {
        composable(route = Screen.Input.route) {
            InputScreen(navController = navController)
        }
        composable(route = Screen.Result.route) { backStackEntry ->
            val sleepHours = backStackEntry.arguments?.getString("sleepHours")?.toFloatOrNull() ?: 0f
            ResultScreen(sleepHours = sleepHours, navController = navController)
        }
    }
}