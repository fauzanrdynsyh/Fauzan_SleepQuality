package com.fauzan0111.fauzan_sleepquality.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fauzan0111.fauzan_sleepquality.R


@Composable
fun DialogHapus(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    AlertDialog(
        text = { Text(text = stringResource(id = R.string.hapus_gambar)) },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(id = R.string.tombol_hapus))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(id = R.string.tombol_batal))
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}