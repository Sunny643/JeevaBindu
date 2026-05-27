package com.jeevabindu.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val JeevaBinduColorScheme = lightColorScheme(
    primary = PrimaryRed,
    onPrimary = CardSurface,
    primaryContainer = LightPinkBg,
    onPrimaryContainer = DarkRed,
    secondary = ReadyGreen,
    onSecondary = CardSurface,
    secondaryContainer = SuccessGreenBg,
    onSecondaryContainer = DarkGreen,
    tertiary = TealAccent,
    onTertiary = CardSurface,
    background = CreamBackground,
    onBackground = DarkText,
    surface = CardSurface,
    onSurface = DarkText,
    surfaceVariant = LightPinkBg,
    onSurfaceVariant = MediumText,
    outline = BorderRed,
    error = EmergencyRed,
    onError = CardSurface,
)

@Composable
fun JeevaBinduTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = JeevaBinduColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = PrimaryRed.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
