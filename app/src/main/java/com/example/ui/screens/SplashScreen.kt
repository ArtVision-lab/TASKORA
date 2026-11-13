package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.TaskoraMark
import com.example.ui.components.TaskoraWordmark
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkTextPrimary
import com.example.ui.theme.DarkTextSecondary
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.LightBackground
import com.example.ui.theme.LightBorder
import com.example.ui.theme.LightTextPrimary
import com.example.ui.theme.LightTextSecondary
import com.example.ui.theme.SoftOrange
import com.example.ui.theme.VibrantOrange
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onDismiss: () -> Unit,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val isDark = MaterialTheme.colorScheme.background == DarkBackground

  // Animation triggers
  var startAnim by remember { mutableStateOf(false) }
  var showText by remember { mutableStateOf(false) }

  // Logo animations: smooth fade-in and gentle scale-up
  val logoAlpha by animateFloatAsState(
    targetValue = if (startAnim) 1f else 0f,
    animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
    label = "logoAlpha"
  )
  val logoScale by animateFloatAsState(
    targetValue = if (startAnim) 1f else 0.80f,
    animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing),
    label = "logoScale"
  )

  // Typography animations: staggered fade-in and subtle slide-up
  val textAlpha by animateFloatAsState(
    targetValue = if (showText) 1f else 0f,
    animationSpec = tween(durationMillis = 550, easing = FastOutSlowInEasing),
    label = "textAlpha"
  )
  val textTranslateY by animateFloatAsState(
    targetValue = if (showText) 0f else 18f,
    animationSpec = tween(durationMillis = 550, easing = FastOutSlowInEasing),
    label = "textTranslateY"
  )

  // Progress loading indicator animation
  val progress by animateFloatAsState(
    targetValue = if (startAnim) 1f else 0f,
    animationSpec = tween(durationMillis = 2300, easing = LinearEasing),
    label = "splashProgress"
  )

  LaunchedEffect(Unit) {
    startAnim = true
    delay(200)
    showText = true
    delay(2300) // Exactly 2.5 seconds total display time
    onDismiss()
  }

  val backgroundColor = if (isDark) DarkBackground else LightBackground
  val backgroundGradient = if (isDark) {
    Brush.radialGradient(
      colors = listOf(
        DarkCard,
        DarkBackground
      ),
      radius = 1200f
    )
  } else {
    Brush.radialGradient(
      colors = listOf(
        Color(0xFFFFFFFF),
        LightBackground
      ),
      radius = 1200f
    )
  }

  val subtitleColor = if (isDark) DarkTextSecondary else LightTextSecondary
  val trackColor = if (isDark) DarkBorder else LightBorder
  val progressColor = VibrantOrange

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(backgroundGradient)
      .clickable { onDismiss() }
      .testTag("splash_screen_container")
  ) {
    // Centered Brand Identity
    Column(
      modifier = Modifier
        .align(Alignment.Center)
        .padding(horizontal = 32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // 1. Logo: Distinctive modern geometric emblem with green gradient & orange checkmark
      Box(
        modifier = Modifier
          .graphicsLayer {
            alpha = logoAlpha
            scaleX = logoScale
            scaleY = logoScale
          }
          .testTag("splash_logo")
      ) {
        TaskoraMark(
          size = 96.dp,
          showNodes = true
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      // 2. Wordmark and Tagline
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .graphicsLayer {
            alpha = textAlpha
            translationY = textTranslateY
          }
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          TaskoraWordmark(
            fontSize = 30.sp,
            letterSpacing = 4.sp,
            isDark = isDark
          )
          Spacer(modifier = Modifier.width(6.dp))
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(VibrantOrange.copy(alpha = if (isDark) 0.25f else 0.15f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "PRO",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = VibrantOrange
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tagline adapted to selected language
        Text(
          text = TaskoraStrings.tagline(language),
          style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 22.sp
          ),
          color = subtitleColor,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Three branding dots from the official brand identity
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .width(28.dp)
              .height(1.dp)
              .background(subtitleColor.copy(alpha = 0.3f))
          )
          Box(
            modifier = Modifier
              .size(5.dp)
              .clip(CircleShape)
              .background(EmeraldGreen)
          )
          Box(
            modifier = Modifier
              .size(6.dp)
              .clip(CircleShape)
              .background(VibrantOrange)
          )
          Box(
            modifier = Modifier
              .size(5.dp)
              .clip(CircleShape)
              .background(SoftOrange)
          )
          Box(
            modifier = Modifier
              .width(28.dp)
              .height(1.dp)
              .background(subtitleColor.copy(alpha = 0.3f))
          )
        }
      }
    }

    // 3. Subtle animated loading indicator near the bottom
    Column(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 48.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .width(130.dp)
          .height(3.5.dp)
          .clip(RoundedCornerShape(2.dp))
          .graphicsLayer { alpha = textAlpha }
          .testTag("splash_loading_bar"),
        color = progressColor,
        trackColor = trackColor
      )

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = if (language == AppLanguage.AR) "جاري التحميل..." else "Loading your workspace...",
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 11.sp,
          letterSpacing = 0.5.sp
        ),
        color = subtitleColor.copy(alpha = 0.75f),
        modifier = Modifier.graphicsLayer { alpha = textAlpha }
      )
    }
  }
}
