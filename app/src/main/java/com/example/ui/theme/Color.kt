package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary deep purple (#7C3AED)
val PurplePrimary = Color(0xFF7C3AED)
val PurpleDark = Color(0xFF5B21B6)
val PurpleLight = Color(0xFF9333EA)
val PurpleSoft = Color(0xFFF3E8FF)
val PurpleBorder = Color(0xFFDDD6FE)

// Progress gradient purple-to-pink
val GradientPurple = Color(0xFF7C3AED)
val GradientPink = Color(0xFFEC4899)
val GradientRose = Color(0xFFF43F5E)

val PurplePinkGradient = Brush.horizontalGradient(
    colors = listOf(GradientPurple, GradientPink)
)

val PurplePinkVerticalGradient = Brush.verticalGradient(
    colors = listOf(GradientPurple, GradientPink)
)

// Gold success accent (#F59E0B) for completed goals
val GoldSuccess = Color(0xFFF59E0B)
val GoldLight = Color(0xFFFEF3C7)
val GoldDark = Color(0xFFD97706)
val GoldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))
)

// Neutral palette with background #FAFAFA
val BackgroundLight = Color(0xFFFAFAFA)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceElevated = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF1E1B4B)
val TextSecondary = Color(0xFF6B7280)
val TextMuted = Color(0xFF9CA3AF)
val BorderLight = Color(0xFFE5E7EB)

// Dark theme palette
val BackgroundDark = Color(0xFF0F0B1E)
val SurfaceDark = Color(0xFF19142E)
val SurfaceElevatedDark = Color(0xFF241D3B)
val TextPrimaryDark = Color(0xFFF9FAFB)
val TextSecondaryDark = Color(0xFFD1D5DB)
val BorderDark = Color(0xFF322A50)
