package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkTextPrimary
import com.example.ui.theme.DarkTextSecondary
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.LightBackground
import com.example.ui.theme.LightTextPrimary
import com.example.ui.theme.LightTextSecondary
import com.example.ui.theme.SoftOrange
import com.example.ui.theme.VibrantOrange

/**
 * Modern & Creative TASKORA Geometric T-Mark with integrated productivity checkmark
 * and floating digital nodes, rendered with the Deep Forest Green & Vibrant Orange brand colors.
 */
@Composable
fun TaskoraMark(
  modifier: Modifier = Modifier,
  size: Dp = 48.dp,
  showNodes: Boolean = true
) {
  val infiniteTransition = rememberInfiniteTransition(label = "mark_glow")
  val pulse by infiniteTransition.animateFloat(
    initialValue = 0.92f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(2400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  Canvas(modifier = modifier.size(size)) {
    val w = this.size.width
    val h = this.size.height

    // Main T Geometry: Deep Forest Green to Emerald Green gradient
    val greenGradient = Brush.linearGradient(
      colors = listOf(
        EmeraldGreen,
        DeepForestGreen
      ),
      start = Offset(0f, 0f),
      end = Offset(w, h)
    )

    // Vibrant Orange accent gradient
    val orangeGradient = Brush.linearGradient(
      colors = listOf(
        VibrantOrange,
        SoftOrange
      ),
      start = Offset(0f, 0f),
      end = Offset(w, h)
    )

    // 1. T Crossbar Path (Curved aerodynamic horizontal bar)
    val crossbarPath = Path().apply {
      moveTo(w * 0.10f, h * 0.32f)
      // Top left sweep
      cubicTo(w * 0.12f, h * 0.20f, w * 0.25f, h * 0.18f, w * 0.35f, h * 0.18f)
      lineTo(w * 0.82f, h * 0.18f)
      // Top right rounded edge
      cubicTo(w * 0.88f, h * 0.18f, w * 0.92f, h * 0.24f, w * 0.88f, h * 0.32f)
      lineTo(w * 0.68f, h * 0.36f)
      // Underbelly of crossbar leading to neck
      cubicTo(w * 0.62f, h * 0.36f, w * 0.58f, h * 0.40f, w * 0.58f, h * 0.46f)
      lineTo(w * 0.42f, h * 0.46f)
      cubicTo(w * 0.42f, h * 0.40f, w * 0.36f, h * 0.36f, w * 0.26f, h * 0.36f)
      close()
    }
    drawPath(path = crossbarPath, brush = greenGradient)

    // 2. T Vertical Central Stem (Solid pillar of organization)
    val stemPath = Path().apply {
      moveTo(w * 0.38f, h * 0.38f)
      lineTo(w * 0.62f, h * 0.38f)
      lineTo(w * 0.60f, h * 0.86f)
      cubicTo(w * 0.60f, h * 0.92f, w * 0.54f, h * 0.94f, w * 0.48f, h * 0.94f)
      lineTo(w * 0.40f, h * 0.94f)
      cubicTo(w * 0.36f, h * 0.94f, w * 0.36f, h * 0.88f, w * 0.38f, h * 0.82f)
      close()
    }
    drawPath(
      path = stemPath,
      brush = Brush.linearGradient(
        colors = listOf(
          DeepForestGreen,
          EmeraldGreen
        ),
        start = Offset(w * 0.5f, h * 0.38f),
        end = Offset(w * 0.5f, h * 0.94f)
      )
    )

    // 3. Integrated Dynamic Checkmark (Vibrant Orange & White trajectory slicing across T neck)
    val checkmarkPath = Path().apply {
      moveTo(w * 0.32f, h * 0.44f)
      lineTo(w * 0.48f, h * 0.56f)
      lineTo(w * 0.84f, h * 0.28f)
    }

    // Outer vibrant orange glow stroke
    drawPath(
      path = checkmarkPath,
      brush = orangeGradient,
      style = Stroke(
        width = w * 0.11f,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )

    // Inner crisp white highlight
    drawPath(
      path = checkmarkPath,
      color = Color.White,
      style = Stroke(
        width = w * 0.055f,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )

    // 4. Floating Digital Square Nodes at top-right (Achievement & Data)
    if (showNodes) {
      val nodeSize = w * 0.09f
      val corner = CornerRadius(nodeSize * 0.25f, nodeSize * 0.25f)

      // Topmost node (Vibrant Orange)
      drawRoundRect(
        brush = orangeGradient,
        topLeft = Offset(w * 0.86f, h * 0.06f),
        size = Size(nodeSize, nodeSize),
        cornerRadius = corner,
        alpha = pulse
      )

      // Middle node (Emerald Green)
      drawRoundRect(
        brush = greenGradient,
        topLeft = Offset(w * 0.76f, h * 0.14f),
        size = Size(nodeSize * 0.85f, nodeSize * 0.85f),
        cornerRadius = corner
      )

      // Right node (Soft Orange)
      drawRoundRect(
        color = SoftOrange,
        topLeft = Offset(w * 0.89f, h * 0.18f),
        size = Size(nodeSize * 0.8f, nodeSize * 0.8f),
        cornerRadius = corner,
        alpha = 0.95f
      )
    }
  }
}

/**
 * Top bar header brand with Taskora mark, stylized wordmark, and PRO badge.
 */
@Composable
fun TaskoraHeaderBrand(
  modifier: Modifier = Modifier,
  language: AppLanguage = AppLanguage.EN,
  showTagline: Boolean = false
) {
  val isDark = MaterialTheme.colorScheme.background == DarkBackground

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Slightly increased logo mark size for stronger brand presence
    TaskoraMark(size = 44.dp)
    Spacer(modifier = Modifier.width(12.dp))
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        TaskoraWordmark(
          fontSize = 19.sp,
          letterSpacing = 1.8.sp,
          isDark = isDark
        )
        Spacer(modifier = Modifier.width(8.dp))
        // Refined PRO badge aligned with brand identity
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(VibrantOrange.copy(alpha = if (isDark) 0.22f else 0.14f))
            .border(
              width = 1.dp,
              color = VibrantOrange.copy(alpha = if (isDark) 0.55f else 0.45f),
              shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 7.dp, vertical = 2.dp)
        ) {
          Text(
            text = "PRO",
            fontSize = 9.5.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.8.sp,
            color = VibrantOrange
          )
        }
      }
      if (showTagline) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = TaskoraStrings.tagline(language),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

/**
 * Stylized TASKORA Wordmark matching the reference logo:
 * - Geometric modern font weight
 * - Vibrant Orange apex triangle accents inside the letter 'A'
 */
@Composable
fun TaskoraWordmark(
  modifier: Modifier = Modifier,
  fontSize: androidx.compose.ui.unit.TextUnit = 24.sp,
  letterSpacing: androidx.compose.ui.unit.TextUnit = 3.sp,
  isDark: Boolean = false
) {
  val textColor = if (isDark) DarkTextPrimary else DeepForestGreen

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "TASK",
      style = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Black,
        fontSize = fontSize,
        letterSpacing = letterSpacing
      ),
      color = textColor
    )
    // Stylized orange dot / accent
    Box(
      modifier = Modifier
        .padding(horizontal = 1.dp)
        .size(fontSize.value.dp * 0.22f)
        .clip(CircleShape)
        .background(VibrantOrange)
    )
    Text(
      text = "ORA",
      style = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Black,
        fontSize = fontSize,
        letterSpacing = letterSpacing
      ),
      color = textColor
    )
  }
}
