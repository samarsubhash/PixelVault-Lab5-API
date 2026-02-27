package com.example.lab5.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lab5.R

// Press Start 2P — pixel/retro Google font (loaded from res/font)
val PressStart2P = FontFamily(
    Font(R.font.press_start_2p, FontWeight.Normal)
)

// Fallback monospace for body text (better readability)
val PixelMono = FontFamily.Monospace

val PixelTypography = Typography(
    // Large display — store title
    displayLarge = TextStyle(
        fontFamily = PressStart2P,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        color = PixelNeonGreen
    ),
    // Section headers
    headlineMedium = TextStyle(
        fontFamily = PressStart2P,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        color = PixelWhite
    ),
    // Card titles
    titleMedium = TextStyle(
        fontFamily = PressStart2P,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 18.sp,
        color = PixelWhite
    ),
    // Small pixel labels
    labelMedium = TextStyle(
        fontFamily = PressStart2P,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        lineHeight = 14.sp,
        color = PixelCyan
    ),
    // Body text — monospace for readability
    bodyLarge = TextStyle(
        fontFamily = PixelMono,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        color = PixelWhite
    ),
    bodyMedium = TextStyle(
        fontFamily = PixelMono,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        color = PixelGray
    ),
    bodySmall = TextStyle(
        fontFamily = PixelMono,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        color = PixelGray
    ),
    // Button text
    labelLarge = TextStyle(
        fontFamily = PressStart2P,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        color = PixelBlack
    )
)