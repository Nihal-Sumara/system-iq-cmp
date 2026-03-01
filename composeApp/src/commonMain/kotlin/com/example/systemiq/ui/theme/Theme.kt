package com.example.systemiq.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Teal40,
    onPrimary = DarkBackground,
    primaryContainer = Teal20,
    onPrimaryContainer = Teal80,
    secondary = Blue40,
    onSecondary = DarkBackground,
    secondaryContainer = Blue20,
    onSecondaryContainer = Blue80,
    tertiary = Amber40,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOnSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = Teal40,
    onPrimary = LightBackground,
    primaryContainer = Teal80,
    onPrimaryContainer = Teal20,
    secondary = Blue40,
    onSecondary = LightBackground,
    secondaryContainer = Blue80,
    onSecondaryContainer = Blue20,
    tertiary = Amber40,
    onTertiary = LightBackground,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOnSurfaceVariant
)

@Composable
fun SystemIQTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
