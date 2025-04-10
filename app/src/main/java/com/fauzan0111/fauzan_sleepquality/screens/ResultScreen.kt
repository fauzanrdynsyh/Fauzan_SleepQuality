package com.fauzan0111.fauzan_sleepquality.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fauzan0111.fauzan_sleepquality.R
import com.fauzan0111.fauzan_sleepquality.ui.theme.Fauzan_SleepQualityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    sleepHours: Float,
    navController: NavController
) {
    val qualityRes = when {
        sleepHours < 6 -> R.string.tidur_kurang
        sleepHours in 6.0..8.0 -> R.string.tidur_cukup
        else -> R.string.tidur_berlebihan
    }

    val imageRes = when {
        sleepHours < 6 -> R.drawable.kurang_tidur
        sleepHours in 6.0..8.0 -> R.drawable.tidur_cukup
        else -> R.drawable.tidur_berlebihan
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.hasil_tidur)) },
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
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.total_tidur, String.format("%.1f", sleepHours)),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.kualitas_tidur, stringResource(qualityRes)),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                navController.navigate("info?from=result")
            }) {
                Text(stringResource(R.string.lihat_tips_tidur))
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
fun ResultScreenPreview() {
    val navController = rememberNavController()
    Fauzan_SleepQualityTheme {
        ResultScreen(sleepHours = 7.5f, navController = navController)
    }
}


