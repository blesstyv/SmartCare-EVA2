package com.example.smartcare.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(

    primary = SmartCareBlue,
    onPrimary = Color.White,

    primaryContainer = Color(0xFFD4ECF8),
    onPrimaryContainer = SmartCareBlueDark,

    secondary = SmartCareTeal,
    onSecondary = Color.White,

    secondaryContainer = SmartCareTealLight,
    onSecondaryContainer = Color(0xFF003C35),

    tertiary = SmartCareAccent,

    background = SmartCareBackground,
    onBackground = SmartCareText,

    surface = SmartCareSurface,
    onSurface = SmartCareText,

    surfaceVariant = SmartCareSurfaceVariant,
    onSurfaceVariant = SmartCareTextSecondary,

    error = SmartCareError
)

private val DarkColorScheme = darkColorScheme(

    primary = SmartCareBlueDarkMode,
    secondary = SmartCareTealDarkMode,

    background = SmartCareDarkBackground,
    surface = SmartCareDarkSurface,

    onBackground = Color(0xFFE3E8EC),
    onSurface = Color(0xFFE3E8EC)
)

private val SmartCareShapes = Shapes(

    small =
        RoundedCornerShape(10.dp),

    medium =
        RoundedCornerShape(16.dp),

    large =
        RoundedCornerShape(24.dp)
)

@Composable
fun SmartCareTheme(
    darkTheme: Boolean =
        isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = SmartCareShapes,
        content = content
    )
}