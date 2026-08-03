package com.zumo.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val ZumoFont = FontFamily.SansSerif  // Inter via system fallback on Android

val ZumoTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        letterSpacing = (-0.5).sp,
        color = ZumoTextPrimary
    ),
    headlineLarge = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = (-0.5).sp,
        color = ZumoTextPrimary
    ),
    headlineMedium = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        color = ZumoTextPrimary
    ),
    titleLarge = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = ZumoTextPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = ZumoTextPrimary
    ),
    bodyLarge = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = ZumoTextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = ZumoTextSecondary
    ),
    bodySmall = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = ZumoTextTertiary
    ),
    labelLarge = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp,
        color = ZumoTextPrimary
    ),
    labelMedium = TextStyle(
        fontFamily = ZumoFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp,
        color = ZumoTextSecondary
    )
)
