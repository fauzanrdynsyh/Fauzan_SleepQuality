package com.fauzan0111.fauzan_sleepquality.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fauzan0111.fauzan_sleepquality.util.calculateSleepHours
import com.fauzan0111.fauzan_sleepquality.nav.Screen


@Composable
fun InputScreen(navController: NavController) {
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    val isValid = sleepTime.isNotBlank() && wakeTime.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Jam Tidur (ex: 22.00)")
        OutlinedTextField(value = sleepTime, onValueChange = { sleepTime = it })

        Spacer(modifier = Modifier.height(8.dp))

        Text("Jam Bangun (ex: 06.00)")
        OutlinedTextField(value = wakeTime, onValueChange = { wakeTime = it })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val sleepHours = calculateSleepHours(sleepTime, wakeTime)
                navController.navigate(Screen.Result.createRoute(sleepHours))
            },
            enabled = isValid
        ) {
            Text("Cek Kualitas Tidur")
        }
    }
}