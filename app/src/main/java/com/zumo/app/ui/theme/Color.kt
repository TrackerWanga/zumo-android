package com.zumo.app.ui.theme

import androidx.compose.ui.graphics.Color

// Base
val ZumoDarkest = Color(0xFF0A0A0D)
val ZumoDark = Color(0xFF121215)
val ZumoDarkSurface = Color(0xFF1A1A1E)
val ZumoDarkGlass = Color(0x991A1A1E)   // 60% opacity for glass
val ZumoBorder = Color(0x14FFFFFF)       // 8% white

// Text
val ZumoTextPrimary = Color(0xFFF5F5F7)
val ZumoTextSecondary = Color(0xFF8E8E93)
val ZumoTextTertiary = Color(0xFF636366)

// User-selectable accent presets
object ZumoAccents {
    val ElectricViolet = AccentPalette(
        name = "Electric Violet",
        primary = Color(0xFF8B5CF6),
        secondary = Color(0xFFA78BFA),
        glow = Color(0x408B5CF6)
    )
    val CyanBlaze = AccentPalette(
        name = "Cyan Blaze",
        primary = Color(0xFF06B6D4),
        secondary = Color(0xFF22D3EE),
        glow = Color(0x4006B6D4)
    )
    val RoseEmber = AccentPalette(
        name = "Rose Ember",
        primary = Color(0xFFF43F5E),
        secondary = Color(0xFFFB7185),
        glow = Color(0x40F43F5E)
    )
    val AmberGold = AccentPalette(
        name = "Amber Gold",
        primary = Color(0xFFF59E0B),
        secondary = Color(0xFFFBBF24),
        glow = Color(0x40F59E0B)
    )
    val Emerald = AccentPalette(
        name = "Emerald",
        primary = Color(0xFF10B981),
        secondary = Color(0xFF34D399),
        glow = Color(0x4010B981)
    )

    val all = listOf(ElectricViolet, CyanBlaze, RoseEmber, AmberGold, Emerald)
    val default = ElectricViolet
}

data class AccentPalette(
    val name: String,
    val primary: Color,
    val secondary: Color,
    val glow: Color
)

// User background presets
object ZumoBackgrounds {
    val DarkGrey = BackgroundPreset(
        name = "Dark Grey",
        base = Color(0xFF121215),
        gridLine = Color(0x1AFFFFFF)
    )
    val DeepBlue = BackgroundPreset(
        name = "Deep Blue",
        base = Color(0xFF0A0E1A),
        gridLine = Color(0x1A3B82F6)
    )
    val Midnight = BackgroundPreset(
        name = "Midnight",
        base = Color(0xFF0D0D14),
        gridLine = Color(0x1A8B5CF6)
    )
    val Obsidian = BackgroundPreset(
        name = "Obsidian",
        base = Color(0xFF0F0F0F),
        gridLine = Color(0x1AFFFFFF)
    )

    val all = listOf(DarkGrey, DeepBlue, Midnight, Obsidian)
    val default = DarkGrey
}

data class BackgroundPreset(
    val name: String,
    val base: Color,
    val gridLine: Color
)
