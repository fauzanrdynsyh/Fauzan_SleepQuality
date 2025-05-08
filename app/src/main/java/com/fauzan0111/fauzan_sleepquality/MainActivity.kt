package com.fauzan0111.fauzan_sleepquality


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.nav.NavGraph
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme
import com.fauzan0111.fauzan_sleepquality.screens.SleepViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fauzan_SleepQualityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(rememberNavController())
                }
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