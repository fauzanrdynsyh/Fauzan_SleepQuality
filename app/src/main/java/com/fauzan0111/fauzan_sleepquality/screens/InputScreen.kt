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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.fauzan0111.fauzan_sleepquality.R


@Composable
fun InputScreen(navController: NavController) {
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    val sleepFocusRequester = remember { FocusRequester() }
    val wakeFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
//    val context = LocalContext.current

    val timePattern = Regex("^([0-1]\\d|2[0-3])\\.(0\\d|[1-5]\\d)$")
    val isSleepValid = timePattern.matches(sleepTime)
    val isWakeValid = timePattern.matches(wakeTime)
    val isValid = isSleepValid && isWakeValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = sleepTime,
            onValueChange = { sleepTime = it },
            label = { Text(stringResource(R.string.jam_tidur)) },
            isError = sleepTime.isNotBlank() && !isSleepValid,
            supportingText = {
                if (sleepTime.isNotBlank() && !isSleepValid) {
                    Text(
                        text = stringResource(R.string.format_salah, "22.00"),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
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

        OutlinedTextField(
            value = wakeTime,
            onValueChange = { wakeTime = it },
            label = { Text(stringResource(R.string.jam_bangun)) },
            isError = wakeTime.isNotBlank() && !isWakeValid,
            supportingText = {
                if (wakeTime.isNotBlank() && !isWakeValid) {
                    Text(
                        text = stringResource(R.string.format_salah,"06.00"),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
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
            Text(stringResource(R.string.cek_kualitas_tidur))
        }
    }
}




