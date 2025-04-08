package com.fauzan0111.fauzan_sleepquality.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fauzan0111.fauzan_sleepquality.util.calculateSleepHours
import com.fauzan0111.fauzan_sleepquality.nav.Screen
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun InputScreen(navController: NavController) {
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    val sleepFocusRequester = remember { FocusRequester() }
    val wakeFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Regex format jam: 00.00 s/d 23.59
    val timePattern = Regex("^([0-1]\\d|2[0-3])\\.(0\\d|[1-5]\\d)$")

    // Validasi input sesuaikan dengan format 00.00 - 23.59, bukan 12 atau 6
    val isValid = timePattern.matches(sleepTime) && timePattern.matches(wakeTime)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Jam Tidur (ex: 22.00)")
        OutlinedTextField(
            value = sleepTime,
            onValueChange = { sleepTime = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(sleepFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    wakeFocusRequester.requestFocus()
                }
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Jam Bangun (ex: 06.00)")
        OutlinedTextField(
            value = wakeTime,
            onValueChange = { wakeTime = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(wakeFocusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            singleLine = true
        )

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

