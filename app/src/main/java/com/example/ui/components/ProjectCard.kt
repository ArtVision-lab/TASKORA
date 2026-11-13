package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ProjectItem
import com.example.data.model.ProjectStatus
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.VibrantOrange
import com.example.ui.theme.WarningAmber

@Composable
fun ProjectCard(
  project: ProjectItem,
  language: AppLanguage,
  modifier: Modifier = Modifier,
  compact: Boolean = false
) {
  val isAr = language == AppLanguage.AR
  val isDark = MaterialTheme.colorScheme.background == DarkBackground
  val displayName = if (isAr) project.nameAr else project.name
  val displayCategory = if (isAr) project.categoryAr else project.category

  val animatedProgress by animateFloatAsState(
    targetValue = (project.progressPercent / 100f).coerceIn(0f, 1f),
    label = "progressAnim"
  )

  val statusColor = when (project.status) {
    ProjectStatus.ON_TRACK -> if (isDark) Color(0xFF34C785) else EmeraldGreen
    ProjectStatus.IN_PROGRESS -> if (isDark) Color(0xFF2EA875) else DeepForestGreen
    ProjectStatus.PLANNING -> if (isDark) WarningAmber else Color(0xFF8F5600)
    ProjectStatus.COMPLETED -> if (isDark) VibrantOrange else Color(0xFFB84E00)
  }

  // Ensure progress bar has sufficient luminosity on dark surfaces
  val progressColor = if (isDark && (project.accentColor == 0xFF174C3CL || project.accentColor == 0xFF174C3C)) {
    Color(0xFF2EA875)
  } else {
    Color(project.accentColor)
  }

  val wsBg = if (isDark) EmeraldGreen.copy(alpha = 0.18f) else DeepForestGreen.copy(alpha = 0.08f)
  val wsText = if (isDark) Color(0xFF34C785) else DeepForestGreen
  val wsBorder = if (isDark) EmeraldGreen.copy(alpha = 0.35f) else DeepForestGreen.copy(alpha = 0.18f)

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colorScheme.surface)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f),
        shape = RoundedCornerShape(16.dp)
      )
      .padding(16.dp)
      .testTag("project_card_${project.id}")
  ) {
    Column {
      // Header: Project Name & Status Pill
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = displayName,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = displayCategory,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Status Pill with border for enhanced contrast
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(statusColor.copy(alpha = if (isDark) 0.20f else 0.12f))
            .border(
              width = 1.dp,
              color = statusColor.copy(alpha = if (isDark) 0.50f else 0.35f),
              shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Box(
            modifier = Modifier
              .size(6.dp)
              .clip(CircleShape)
              .background(statusColor)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = TaskoraStrings.statusLabel(project.status, language),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = statusColor
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Progress bar and percentage
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = TaskoraStrings.progressSummary(project.completedTasks, project.totalTasks, language),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = "${project.progressPercent}%",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold
          ),
          color = if (isDark) Color(0xFF34C785) else EmeraldGreen
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = progressColor,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        strokeCap = StrokeCap.Round
      )

      if (!compact) {
        Spacer(modifier = Modifier.height(12.dp))

        // Dates & Workspace footer
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${project.startDate} → ${project.dueDate}",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(wsBg)
              .border(1.dp, wsBorder, RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = TaskoraStrings.workspace(project.workspace, language),
              fontSize = 10.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = wsText
            )
          }
        }
      }
    }
  }
}
