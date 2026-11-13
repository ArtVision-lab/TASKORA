package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.data.model.AppThemeMode

private val DarkColorScheme = darkColorScheme(
  primary = EmeraldGreen,
  onPrimary = Color.White,
  primaryContainer = EmeraldGreen.copy(alpha = 0.25f),
  onPrimaryContainer = Color(0xFFA3E5C8),
  secondary = VibrantOrange,
  onSecondary = Color.White,
  secondaryContainer = VibrantOrange.copy(alpha = 0.22f),
  onSecondaryContainer = SoftOrange,
  tertiary = EmeraldGreen,
  onTertiary = Color.White,
  background = DarkBackground,
  onBackground = DarkTextPrimary,
  surface = DarkCard,
  onSurface = DarkTextPrimary,
  surfaceVariant = DarkSurfaceVariant,
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkBorder,
  error = ErrorCoral,
  onError = Color.White
)

private val LightColorScheme = lightColorScheme(
  primary = DeepForestGreen,
  onPrimary = Color.White,
  primaryContainer = EmeraldGreen.copy(alpha = 0.14f),
  onPrimaryContainer = DeepForestGreen,
  secondary = VibrantOrange,
  onSecondary = Color.White,
  secondaryContainer = SoftOrange.copy(alpha = 0.18f),
  onSecondaryContainer = Color(0xFFB35300),
  tertiary = EmeraldGreen,
  onTertiary = Color.White,
  background = LightBackground,
  onBackground = LightTextPrimary,
  surface = LightCard,
  onSurface = LightTextPrimary,
  surfaceVariant = LightSurfaceVariant,
  onSurfaceVariant = LightTextSecondary,
  outline = LightBorder,
  error = ErrorCoral,
  onError = Color.White
)

@Composable
fun TaskoraTheme(
  themeMode: AppThemeMode = AppThemeMode.SYSTEM,
  isArabic: Boolean = false,
  content: @Composable () -> Unit
) {
  val systemDark = isSystemInDarkTheme()
  val darkTheme = when (themeMode) {
    AppThemeMode.LIGHT -> false
    AppThemeMode.DARK -> true
    AppThemeMode.SYSTEM -> systemDark
  }

  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  val typography = getTaskoraTypography(isArabic)

  MaterialTheme(
    colorScheme = colorScheme,
    typography = typography,
    content = content
  )
}
