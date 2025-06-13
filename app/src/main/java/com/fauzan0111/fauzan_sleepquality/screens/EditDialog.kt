package com.fauzan0111.fauzan_sleepquality.screens

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.model.Tidur
import com.fauzan0111.fauzan_sleepquality.network.TidurApi

@Composable
fun EditDialog(
    tidur: Tidur,
    onDismissRequest: () -> Unit,
    onUpdateClick: (String, String, String, Bitmap?) -> Unit
) {
    val context = LocalContext.current
    var editedWaktuTidur by remember {
        mutableStateOf(tidur.waktuTidur.substringBeforeLast(":"))
    }
    var editedWaktuBangun by remember {
        mutableStateOf(tidur.waktuBangun.substringBeforeLast(":"))
    }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            selectedBitmap = getCroppedImage(context.contentResolver, result)
        } else {
            Log.e("EditTidurDialog", "Gagal meng-crop gambar: ${result.error}")
            Toast.makeText(context, "Gagal memilih gambar baru", Toast.LENGTH_SHORT).show()
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        title = { Text(text = "Edit Data Tidur") },
        text = {
            Column {
                if (selectedBitmap != null) {
                    Image(
                        bitmap = selectedBitmap!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } else if (tidur.imageId.isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(TidurApi.getTidurUrl(tidur.imageId))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .align(Alignment.CenterHorizontally),
                        error = painterResource(id = R.drawable.baseline_broken_image_24)
                    )
                } else {
                    Spacer(modifier = Modifier.height(128.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = editedWaktuTidur,
                    onValueChange = { editedWaktuTidur = it },
                    label = { Text("Waktu Tidur") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = editedWaktuBangun,
                    onValueChange = { editedWaktuBangun = it },
                    label = { Text("Waktu Bangun") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val options = CropImageContractOptions(
                            null, CropImageOptions(
                                imageSourceIncludeGallery = true,
                                imageSourceIncludeCamera = true,
                                fixAspectRatio = true
                            )
                        )
                        imageCropLauncher.launch(options)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ganti Gambar")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdateClick(tidur.id, editedWaktuTidur, editedWaktuBangun, selectedBitmap)
                }
            ) {
                Text("Simpan Perubahan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Batal")
            }
        }
    )
}