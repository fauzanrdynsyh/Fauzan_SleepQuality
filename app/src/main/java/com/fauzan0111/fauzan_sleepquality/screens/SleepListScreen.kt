package com.fauzan0111.fauzan_sleepquality.screens


import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.model.SleepRecord
import com.fauzan0111.fauzan_sleepquality.nav.Screen
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme
import com.fauzan0111.fauzan_sleepquality.util.SleepViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepListScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.list_tidur))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.TambahDataTidur.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_data),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding),
            navController = navController,

        )
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier, navController: NavController ) {
    val context = LocalContext.current
    val factory = SleepViewModelFactory(context)
    val viewModel: SleepViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Bedtime,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.data_tidur_kosong),
                style = MaterialTheme.typography.titleMedium
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) {
                SleepQualityListItem(sleepData = it) {
                    navController.navigate(Screen.EditDataTidur.withId(it.id))
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun SleepQualityListItem(sleepData: SleepRecord, onClick: () -> Unit) {
    val sleepQualityColors = listOf(
        MaterialTheme.colorScheme.error, // 1 - Sangat buruk
        MaterialTheme.colorScheme.errorContainer, // 2 - Buruk
        MaterialTheme.colorScheme.tertiary, // 3 - Sedang
        MaterialTheme.colorScheme.secondaryContainer, // 4 - Baik
        MaterialTheme.colorScheme.primary, // 5 - Sangat baik
    )

    val sleepQualityLabels = listOf(
        stringResource(id = R.string.sangat_buruk),
        stringResource(id = R.string.buruk),
        stringResource(id = R.string.sedang),
        stringResource(id = R.string.baik),
        stringResource(id = R.string.sangat_baik)
    )

    val qualityColor = sleepQualityColors.getOrElse(sleepData.kualitasTidur - 1) { MaterialTheme.colorScheme.tertiary }
    val qualityLabel = sleepQualityLabels.getOrElse(sleepData.kualitasTidur - 1) { stringResource(id = R.string.sedang) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sleepData.tanggal,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = qualityColor.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = when (sleepData.kualitasTidur) {
                            1, 2 -> Icons.Default.SentimentVeryDissatisfied
                            3 -> Icons.Default.SentimentNeutral
                            else -> Icons.Default.SentimentVerySatisfied
                        },
                        contentDescription = null,
                        tint = qualityColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = qualityLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = qualityColor
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Bedtime,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${sleepData.waktuTidur} → ${sleepData.waktuBangun}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = String.format("%.1f jam", sleepData.durasiTidur),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview (showBackground = true)
@Preview (
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true
)

@Composable
fun SleepListScreenPreview(){
    val navController = rememberNavController()
    Fauzan_SleepQualityTheme {
        ResultScreen(navController = navController)
    }
}


