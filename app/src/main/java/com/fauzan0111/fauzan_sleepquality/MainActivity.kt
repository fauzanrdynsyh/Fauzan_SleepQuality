package com.fauzan0111.fauzan_sleepquality


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.nav.NavGraph
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fauzan_SleepQualityTheme {
                SleepApp()
            }
        }
    }
}

@Composable
fun SleepApp() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}