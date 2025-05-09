package com.fauzan0111.fauzan_sleepquality.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme
import com.fauzan0111.fauzan_sleepquality.util.SleepViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val KEY_ID_DATA = "id"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(id: Long? = null, navController: NavController) {
    val context = LocalContext.current
    val factory = SleepViewModelFactory(context)
    val viewModel: ResultViewModel = viewModel(factory = factory)

    var tanggal by remember { mutableStateOf(getCurrentDate()) }
    var waktuTidur by remember { mutableStateOf("") }
    var waktuBangun by remember { mutableStateOf("") }
    var kualitasTidur by remember { mutableIntStateOf(3) }
    var showDialog by remember { mutableStateOf(false) }

    val timePattern = Regex("^([0-1]\\d|2[0-3])\\.(0\\d|[1-5]\\d)$")
    val isTidurValid = timePattern.matches(waktuTidur)
    val isBangunValid = timePattern.matches(waktuBangun)
    val isInputValid = isTidurValid && isBangunValid


    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getById(id) ?: return@LaunchedEffect
        tanggal = data.tanggal
        waktuTidur = data.waktuTidur
        waktuBangun = data.waktuBangun
        kualitasTidur = data.kualitasTidur
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_data_tidur))
                    else
                        Text(text = stringResource(id = R.string.edit_data_tidur))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (tanggal.isEmpty() || waktuTidur.isEmpty() || waktuBangun.isEmpty() || !isInputValid) {
                            Toast.makeText(context, R.string.invalid_data, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(tanggal, waktuTidur, waktuBangun, kualitasTidur)
                        } else {
                            viewModel.update(id, tanggal, waktuTidur, waktuBangun, kualitasTidur)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormSleepQuality(
            tanggal = tanggal,
            onTanggalChange = { tanggal = it },
            waktuTidur = waktuTidur,
            onWaktuTidurChange = { waktuTidur = it },
            waktuBangun = waktuBangun,
            onWaktuBangunChange = { waktuBangun = it },
            kualitasTidur = kualitasTidur,
            onKualitasTidurChange = { kualitasTidur = it },
            isTidurValid = isTidurValid,
            isBangunValid = isBangunValid,
            modifier = Modifier.padding(padding)
        )

        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false}) {
                showDialog = false
                viewModel.delete(id)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormSleepQuality(
    tanggal: String, onTanggalChange: (String) -> Unit,
    waktuTidur: String, onWaktuTidurChange: (String) -> Unit,
    waktuBangun: String, onWaktuBangunChange: (String) -> Unit,
    kualitasTidur: Int, onKualitasTidurChange: (Int) -> Unit,
    isTidurValid: Boolean,
    isBangunValid: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tanggal
        OutlinedTextField(
            value = tanggal,
            onValueChange = { onTanggalChange(it) },
            label = { Text(text = stringResource(R.string.tanggal)) },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "Calendar")
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Di sini Anda dapat menampilkan DatePicker dialog
                    // Untuk implementasi sederhana kita gunakan TextField saja
                }
        )

        // Waktu Tidur
        OutlinedTextField(
            value = waktuTidur,
            onValueChange = { onWaktuTidurChange(it) },
            label = { Text(text = stringResource(R.string.waktu_tidur)) },
            isError = waktuTidur.isNotEmpty() && !isTidurValid,
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Bedtime, contentDescription = "Bedtime")
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (waktuTidur.isNotEmpty() && !isTidurValid) {
            Text(
                text = "Format salah (contoh: 22.30)",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Waktu Bangun
        OutlinedTextField(
            value = waktuBangun,
            onValueChange = { onWaktuBangunChange(it) },
            label = { Text(text = stringResource(R.string.waktu_bangun)) },
            isError = waktuBangun.isNotEmpty() && !isBangunValid,
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.WbSunny, contentDescription = "Wake up")
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (waktuBangun.isNotEmpty() && !isBangunValid) {
            Text(
                text = "Format salah (contoh: 06.15)",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Kualitas Tidur
        Text(
            text = stringResource(R.string.kualitas_tidur, kualitasTidur.toFloat()),
            style = MaterialTheme.typography.bodyLarge
        )

        Slider(
            value = kualitasTidur.toFloat(),
            onValueChange = { onKualitasTidurChange(it.toInt()) },
            valueRange = 1f..5f,
            steps = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.buruk), style = MaterialTheme.typography.bodySmall)
            Text(text = stringResource(R.string.sedang), style = MaterialTheme.typography.bodySmall)
            Text(text = stringResource(R.string.baik), style = MaterialTheme.typography.bodySmall)
        }

        Text(
            text = stringResource(R.string.kualitas_tidur, kualitasTidur.toFloat()),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

// Fungsi untuk mendapatkan tanggal saat ini dalam format yang sesuai
private fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date())
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true
)
@Composable
fun ResultScreenPreview() {
    val navController = rememberNavController()
    Fauzan_SleepQualityTheme {
        ResultScreen(navController = navController)
    }
}


