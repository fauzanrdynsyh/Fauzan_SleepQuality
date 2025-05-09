package com.fauzan0111.fauzan_sleepquality


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.nav.NavGraph
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme
import com.fauzan0111.fauzan_sleepquality.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dataStore = SettingsDataStore(LocalContext.current)
            val tema by dataStore.getTheme().collectAsState(initial = false)

            Fauzan_SleepQualityTheme(temaGelap = tema) {
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