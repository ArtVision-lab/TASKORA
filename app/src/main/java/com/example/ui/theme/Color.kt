package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// ==================================================
// OFFICIAL TASKORA BRAND COLOR SYSTEM (Forest Green & Vibrant Orange)
// ==================================================

// Primary Colors
val DeepForestGreen = Color(0xFF174C3C)
val EmeraldGreen = Color(0xFF267653)

// Accent Colors
val VibrantOrange = Color(0xFFFF8A35)
val SoftOrange = Color(0xFFFFB36B)

// Light Theme Tokens (High-Contrast WCAG 2.1 AA Compliant)
val LightBackground = Color(0xFFF7F8F5)
val LightCard = Color(0xFFFFFFFF)
val LightTextPrimary = Color(0xFF13221A) // High-contrast charcoal green (15.6:1 on white)
val LightTextSecondary = Color(0xFF45594E) // Legible slate green (6.1:1 on white, exceeds 4.5:1 WCAG AA)
val LightBorder = Color(0xFFCFD8CF) // Crisp visible border for cards, inputs and tabs
val LightSurfaceVariant = Color(0xFFEBF0EA)
val LightTextDisabled = Color(0xFF86978E)

// Dark Theme Tokens
val DarkBackground = Color(0xFF101C18)
val DarkCard = Color(0xFF1B2B24)
val DarkElevatedCard = Color(0xFF22372D)
val DarkTextPrimary = Color(0xFFF2F6F2)
val DarkTextSecondary = Color(0xFFA5B2A9)
val DarkBorder = Color(0xFF34483C)
val DarkSurfaceVariant = DarkElevatedCard

// High-contrast bright emerald for dark mode icons and indicators
val BrightEmeraldGreen = Color(0xFF34C785)

// Semantic Colors
val SuccessGreen = Color(0xFF299D69)
val WarningAmber = Color(0xFFF2A541)
val ErrorCoral = Color(0xFFDC5B58)

// Compatibility aliases so any legacy references map directly to the new green & orange system
val PrimaryGreen = DeepForestGreen
val AccentOrange = VibrantOrange
val PrimaryBlue = DeepForestGreen // Mapped to DeepForestGreen
val SecondaryViolet = VibrantOrange // Mapped to VibrantOrange
