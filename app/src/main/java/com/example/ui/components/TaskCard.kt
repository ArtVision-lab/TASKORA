package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.Priority
import com.example.data.model.TaskItem
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.VibrantOrange
import com.example.ui.theme.WarningAmber

/**
 * Refined TASKORA Task Card:
 * - High title readability and clear visual hierarchy
 * - Distinctive completion checkbox with high visibility in both light & dark themes
 * - Polished priority badge with subtle border and brand tints
 * - High-contrast date and workspace metadata tags
 */
@Composable
fun TaskCard(
  task: TaskItem,
  onToggleComplete: () -> Unit,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val isAr = language == AppLanguage.AR
  val isDark = MaterialTheme.colorScheme.background == DarkBackground
  val displayTitle = if (isAr) task.titleAr else task.title
  val displayDesc = if (isAr) task.descriptionAr else task.description
  val displayDue = if (isAr) task.dueDateLabelAr else task.dueDateLabel

  val checkScale by animateFloatAsState(
    targetValue = if (task.isCompleted) 1.0f else 0.85f,
    animationSpec = tween(180),
    label = "checkScale"
  )
  val checkBg by animateColorAsState(
    targetValue = if (task.isCompleted) EmeraldGreen else if (isDark) EmeraldGreen.copy(alpha = 0.08f) else Color.Transparent,
    animationSpec = tween(180),
    label = "checkBg"
  )
  val checkBorder by animateColorAsState(
    targetValue = if (task.isCompleted) {
      if (isDark) Color(0xFF34C785) else EmeraldGreen
    } else {
      if (isDark) Color(0xFF34C785).copy(alpha = 0.6f) else EmeraldGreen.copy(alpha = 0.5f)
    },
    animationSpec = tween(180),
    label = "checkBorder"
  )

  val priorityColor = when (task.priority) {
    Priority.HIGH -> VibrantOrange
    Priority.MEDIUM -> WarningAmber
    Priority.LOW -> SuccessGreen
  }

  // High-contrast priority text color to ensure WCAG AA (>= 4.5:1) in Light Mode
  val priorityTextColor = if (isDark) {
    priorityColor
  } else {
    when (task.priority) {
      Priority.HIGH -> Color(0xFFB84E00)
      Priority.MEDIUM -> Color(0xFF8F5600)
      Priority.LOW -> Color(0xFF1E754D)
    }
  }

  // Workspace tag colors adapt for accessible contrast in both themes
  val workspaceBg = if (isDark) EmeraldGreen.copy(alpha = 0.18f) else DeepForestGreen.copy(alpha = 0.08f)
  val workspaceText = if (isDark) Color(0xFF34C785) else DeepForestGreen
  val workspaceBorder = if (isDark) EmeraldGreen.copy(alpha = 0.35f) else DeepForestGreen.copy(alpha = 0.18f)

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
      .clickable { onToggleComplete() }
      .padding(14.dp)
      .testTag("task_item_${task.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.Top
    ) {
      // Accessible circular completion toggle button with min 48dp touch area
      Box(
        modifier = Modifier
          .size(48.dp)
          .clickable { onToggleComplete() }
          .testTag("task_checkbox_${task.id}"),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(checkBg)
            .border(
              width = if (task.isCompleted) 2.dp else 1.8.dp,
              color = checkBorder,
              shape = CircleShape
            )
            .scale(checkScale),
          contentAlignment = Alignment.Center
        ) {
          if (task.isCompleted) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "Completed",
              tint = Color.White,
              modifier = Modifier.size(15.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(6.dp))

      Column(
        modifier = Modifier.weight(1f)
      ) {
        // Title & Priority Pill
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = displayTitle,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              lineHeight = 21.sp,
              textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            ),
            color = if (task.isCompleted) {
              MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f)
            } else {
              MaterialTheme.colorScheme.onSurface
            },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
          )

          Spacer(modifier = Modifier.width(10.dp))

          // Refined priority badge with high-contrast text
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(7.dp))
              .background(priorityColor.copy(alpha = if (isDark) 0.20f else 0.14f))
              .border(
                width = 1.dp,
                color = priorityColor.copy(alpha = if (isDark) 0.55f else 0.45f),
                shape = RoundedCornerShape(7.dp)
              )
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = TaskoraStrings.priorityLabel(task.priority, language),
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.3.sp,
              color = priorityTextColor
            )
          }
        }

        if (displayDesc.isNotBlank()) {
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = displayDesc,
            style = MaterialTheme.typography.bodySmall.copy(
              lineHeight = 17.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Metadata Tags: Due date & Workspace
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Due Date Chip
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (isDark) 0.65f else 0.85f))
              .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(6.dp)
              )
              .padding(horizontal = 7.dp, vertical = 3.dp)
          ) {
            Icon(
              imageVector = Icons.Outlined.CalendarToday,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = displayDue,
              fontSize = 10.5.sp,
              color = MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.Medium
            )
          }

          // Workspace Chip (with high-contrast dark theme adaptation)
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(workspaceBg)
              .border(
                width = 1.dp,
                color = workspaceBorder,
                shape = RoundedCornerShape(6.dp)
              )
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = TaskoraStrings.workspace(task.workspace, language),
              fontSize = 10.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = workspaceText
            )
          }

          task.projectName?.let { pName ->
            Text(
              text = "• $pName",
              fontSize = 10.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }
      }
    }
  }
}
