package com.fauzan0111.fauzan_sleepquality.screens

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme

@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        text = { Text(text = stringResource(R.string.pesan_hapus)) },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(R.string.tombol_hapus))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource((R.string.tombol_batal)))
            }
        },
        onDismissRequest = { onDismissRequest()}
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview(){
    Fauzan_SleepQualityTheme {
        DisplayAlertDialog(
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}

