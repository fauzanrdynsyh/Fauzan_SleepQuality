package com.fauzan0111.fauzan_sleepquality.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import android.content.res.Configuration
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.model.SleepCategory
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen() {
    val defaultCategory = stringResource(R.string.tidur_cukup)
    var selectedCategory by remember { mutableStateOf(defaultCategory) }
    val categories = listOf(
        stringResource(R.string.tidur_cukup),
        stringResource(R.string.tidur_kurang),
        stringResource(R.string.tidur_berlebihan)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.info_tidur)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.sleep_info), // ganti dengan gambar kamu
                    contentDescription = stringResource(R.string.illustration_tidur),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.info_deskripsi_awal),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.pilih_kategori_tidur)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (selectedCategory) {
                    stringResource(R.string.tidur_cukup) -> {
                        val tips = listOf(
                            Pair(R.string.tips_tidur_cukup_1, R.drawable.jadwal_tidur),
                            Pair(R.string.tips_tidur_cukup_2, R.drawable.lingkungan_tidur),
                            Pair(R.string.tips_tidur_cukup_3, R.drawable.hindari_kafein)
                        )

                        tips.forEach { (tipRes, imageRes) ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = stringResource(tipRes))
                            Spacer(modifier = Modifier.height(4.dp))
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    stringResource(R.string.tidur_kurang) -> {
                        val tips = listOf(
                            Pair(R.string.tips_tidur_kurang_1, R.drawable.tidur_awal),
                            Pair(R.string.tips_tidur_kurang_2, R.drawable.relaksasi),
                            Pair(R.string.tips_tidur_kurang_3, R.drawable.no_kafein)
                        )

                        tips.forEach { (tipRes, imageRes) ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = stringResource(tipRes))
                            Spacer(modifier = Modifier.height(4.dp))
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    stringResource(R.string.tidur_berlebihan) -> {
                        val tips = listOf(
                            Pair(R.string.tips_tidur_berlebihan_1, R.drawable.morning_routine),
                            Pair(R.string.tips_tidur_berlebihan_2, R.drawable.tidur_siang),
                            Pair(R.string.tips_tidur_berlebihan_3, R.drawable.terapkan_waktu)
                        )

                        tips.forEach { (tipRes, imageRes) ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = stringResource(tipRes))
                            Spacer(modifier = Modifier.height(4.dp))
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

val sleepCategories = listOf(
    SleepCategory(
        title = R.string.tidur_cukup,
        description = R.string.desc_tidur_cukup,
        tips = listOf(R.string.tips_tidur_cukup_1, R.string.tips_tidur_cukup_2)
    ),
    SleepCategory(
        title = R.string.tidur_kurang,
        description = R.string.desc_tidur_kurang,
        tips = listOf(R.string.tips_tidur_kurang_1, R.string.tips_tidur_kurang_2)
    ),
    SleepCategory(
        title = R.string.tidur_berlebihan,
        description = R.string.desc_tidur_berlebihan,
        tips = listOf(R.string.tips_tidur_berlebihan_1, R.string.tips_tidur_berlebihan_2)
    )
)

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun InfoScreenPreview() {
    Fauzan_SleepQualityTheme {
        InfoScreen()
    }
}
