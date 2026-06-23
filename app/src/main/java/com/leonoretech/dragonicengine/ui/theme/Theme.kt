package com.leonoretech.dragonicengine.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DragonicColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonMagenta,
    error = DangerRed,
    background = BgBlack,
    surface = PanelDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun DragonicEngineTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DragonicColorScheme,
        typography = DragonicTypography,
        content = content
    )
}
