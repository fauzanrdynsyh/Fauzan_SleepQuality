package com.fauzan0111.fauzan_sleepquality.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


val MidnightBlue = Color(0xFF0D1B2A)
val Moonlight = Color(0xFF778DA9)
val SoftPurple = Color(0xFF9A8C98)
val NightText = Color(0xFFE0E1DD)
val ButtonPurple = Color(0xFF6C63FF)

private val DarkColorScheme = darkColorScheme(
    primary = ButtonPurple,
    secondary = SoftPurple,
    tertiary = Moonlight,
    background = MidnightBlue,
    surface = MidnightBlue,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = NightText,
    onSurface = NightText,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */

)

@Composable
fun Fauzan_SleepQualityTheme(
    temaGelap: Boolean = false,
    content: @Composable () -> Unit
) {
    val warnaSkema = if (temaGelap) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = warnaSkema,
        typography = Typography,
        content = content
    )
}