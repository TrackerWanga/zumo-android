package com.zumo.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ZumoAccents.default.primary,
    secondary = ZumoAccents.default.secondary,
    background = ZumoDark,
    surface = ZumoDarkSurface,
    surfaceVariant = ZumoDarkGlass,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = ZumoTextPrimary,
    onSurface = ZumoTextPrimary,
    outline = ZumoBorder
)

@Composable
fun ZumoTheme(
    accent: AccentPalette = ZumoAccents.default,
    background: BackgroundPreset = ZumoBackgrounds.default,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme.copy(
        primary = accent.primary,
        secondary = accent.secondary
    )

    CompositionLocalProvider(
        LocalZumoAccent provides accent,
        LocalZumoBackground provides background
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = ZumoTypography,
            content = content
        )
    }
}

val LocalZumoAccent = staticCompositionLocalOf { ZumoAccents.default }
val LocalZumoBackground = staticCompositionLocalOf { ZumoBackgrounds.default }
