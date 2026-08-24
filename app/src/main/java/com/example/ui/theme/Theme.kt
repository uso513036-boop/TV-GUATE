package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val GuateDarkColorScheme = darkColorScheme(
    primary = GuateBlueSecondary,
    onPrimary = Color(0xFF082F49),
    primaryContainer = GuateBlueContainer,
    onPrimaryContainer = Color(0xFFE0F2FE),
    secondary = GuateBlueTertiary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF075985),
    onSecondaryContainer = Color(0xFFBAE6FD),
    tertiary = GuateShieldGreen,
    onTertiary = Color.White,
    background = GuateDarkBackground,
    onBackground = GuateLightText,
    surface = GuateDarkSurface,
    onSurface = GuateLightText,
    surfaceVariant = GuateDarkSurfaceVariant,
    onSurfaceVariant = GuateGrayText,
    outline = GuateDarkBorder,
    error = GuateLiveRed,
    onError = Color.White
)

private val GuateLightColorScheme = lightColorScheme(
    primary = GuateBluePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBAE6FD),
    onPrimaryContainer = Color(0xFF0C4A6E),
    secondary = GuateBlueTertiary,
    onSecondary = Color.White,
    tertiary = GuateShieldGreen,
    onTertiary = Color.White,
    background = Color(0xFFF1F5F9),
    onBackground = Color(0xFF0F172A),
    surface = Color.White,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF475569),
    outline = Color(0xFFCBD5E1),
    error = GuateLiveRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to dark for TV and streaming experience
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) GuateDarkColorScheme else GuateLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = !darkTheme
                insetsController.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
