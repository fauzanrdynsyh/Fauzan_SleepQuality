package com.fauzan0111.fauzan_sleepquality.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    sleepHours: Float,
    navController: NavController
) {
    val quality = when {
        sleepHours < 6 -> "Kurang tidur"
        sleepHours in 6.0..8.0 -> "Tidur cukup"
        else -> "Tidur berlebihan"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hasil Tidur") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total tidur: %.1f jam".format(sleepHours),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Kualitas: $quality",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
