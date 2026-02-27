package com.example.lab5.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PixelDarkColorScheme = darkColorScheme(
    primary = PixelNeonGreen,
    onPrimary = PixelBlack,
    primaryContainer = PixelNeonGreenDim,
    onPrimaryContainer = PixelBlack,
    secondary = PixelCyan,
    onSecondary = PixelBlack,
    secondaryContainer = PixelCyanDim,
    onSecondaryContainer = PixelBlack,
    tertiary = PixelOrange,
    onTertiary = PixelBlack,
    tertiaryContainer = PixelGold,
    onTertiaryContainer = PixelBlack,
    background = PixelDarkPurple,
    onBackground = PixelWhite,
    surface = PixelDarkSurface,
    onSurface = PixelWhite,
    surfaceVariant = PixelCardBg,
    onSurfaceVariant = PixelGray,
    error = PixelRed,
    onError = PixelBlack,
    outline = PixelNeonGreen
)

@Composable
fun Lab5Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = PixelDarkColorScheme // Always dark — retro aesthetic

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = PixelBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PixelTypography,
        content = content
    )
}