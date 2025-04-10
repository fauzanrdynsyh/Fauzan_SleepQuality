package com.fauzan0111.fauzan_sleepquality.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.model.SleepCategory
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavController) {
    val defaultCategory = stringResource(R.string.tidur_cukup)
    var selectedCategory by remember { mutableStateOf(defaultCategory) }

    val categories = listOf(
        stringResource(R.string.tidur_cukup),
        stringResource(R.string.tidur_kurang),
        stringResource(R.string.tidur_berlebihan)
    )

    val sleepCategories = listOf(
        SleepCategory(
            title = stringResource(R.string.tidur_cukup),
            description = stringResource(R.string.desc_tidur_cukup),
            tips = listOf(
                Pair(R.string.tips_tidur_cukup_1, R.drawable.jadwal_tidur),
                Pair(R.string.tips_tidur_cukup_2, R.drawable.lingkungan_tidur),
                Pair(R.string.tips_tidur_cukup_3, R.drawable.hindari_kafein)
            )
        ),
        SleepCategory(
            title = stringResource(R.string.tidur_kurang),
            description = stringResource(R.string.desc_tidur_kurang),
            tips = listOf(
                Pair(R.string.tips_tidur_kurang_1, R.drawable.tidur_awal),
                Pair(R.string.tips_tidur_kurang_2, R.drawable.relaksasi),
                Pair(R.string.tips_tidur_kurang_3, R.drawable.no_kafein)
            )
        ),
        SleepCategory(
            title = stringResource(R.string.tidur_berlebihan),
            description = stringResource(R.string.desc_tidur_berlebihan),
            tips = listOf(
                Pair(R.string.tips_tidur_berlebihan_1, R.drawable.morning_routine),
                Pair(R.string.tips_tidur_berlebihan_2, R.drawable.tidur_siang),
                Pair(R.string.tips_tidur_berlebihan_3, R.drawable.terapkan_waktu)
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.info_tidur)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.sleep_info),
                    contentDescription = stringResource(R.string.illustration_tidur),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
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
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth() // <-- pindah ke sini
                ) {
                    TextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.pilih_kategori_tidur)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier.fillMaxWidth() // bisa tetap di sini juga sih
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

                // Show selected category content
                val selected = sleepCategories.find { it.title == selectedCategory }

                selected?.let { category ->
                    Text(
                        text = category.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    category.tips.forEach { (tipRes, imageRes) ->
                        Text(text = stringResource(tipRes))
                        Spacer(modifier = Modifier.height(4.dp))
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun InfoScreenPreview() {
    val navController = rememberNavController()
    Fauzan_SleepQualityTheme {
        InfoScreen(navController = navController)
    }
}
