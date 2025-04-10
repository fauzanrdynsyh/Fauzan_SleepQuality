package com.fauzan0111.fauzan_sleepquality.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fauzan0111.fauzan_sleepquality.nav.Screen
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme
import com.fauzan0111.fauzan_sleepquality.util.calculateSleepHours


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavController) {
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    val sleepFocusRequester = remember { FocusRequester() }
    val wakeFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val timePattern = Regex("^([0-1]\\d|2[0-3])\\.(0\\d|[1-5]\\d)$")
    val isSleepValid = timePattern.matches(sleepTime)
    val isWakeValid = timePattern.matches(wakeTime)
    val isValid = isSleepValid && isWakeValid

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.input_tidur)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    onNext = { wakeFocusRequester.requestFocus() }
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
                            text = stringResource(R.string.format_salah, "06.00"),
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
                    onDone = { keyboardController?.hide() }
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
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true
)
@Composable
fun InputScreenPreview() {
    val navController = rememberNavController()
    Fauzan_SleepQualityTheme {
        InputScreen(navController = navController)
    }
}





