package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.WorkspaceType
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.BrightEmeraldGreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkElevatedCard
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VibrantOrange

/**
 * Polished, non-clipping horizontal workspace selector supporting:
 * - All Workspaces
 * - Personal
 * - Professional
 * - Projects
 * Built with LazyRow for smooth horizontal scrolling, zero edge-clipping, and accessible touch targets.
 */
@Composable
fun WorkspaceSelectorTabs(
  selectedWorkspace: WorkspaceType,
  onWorkspaceSelected: (WorkspaceType) -> Unit,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val isDark = isSystemInDarkTheme() || MaterialTheme.colorScheme.background == DarkBackground
  val workspaces = listOf(
    WorkspaceType.ALL,
    WorkspaceType.PERSONAL,
    WorkspaceType.PROFESSIONAL,
    WorkspaceType.PROJECTS
  )

  LazyRow(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    items(workspaces, key = { it.name }) { workspace ->
      val isSelected = workspace == selectedWorkspace

      val activeBg = if (isDark) EmeraldGreen else DeepForestGreen
      val activeBorder = if (isDark) BrightEmeraldGreen else EmeraldGreen

      val unselectedBg = if (isDark) DarkElevatedCard else MaterialTheme.colorScheme.surface
      val unselectedBorder = MaterialTheme.colorScheme.outline.copy(alpha = if (isDark) 0.6f else 0.8f)

      val bgColor by animateColorAsState(
        targetValue = if (isSelected) activeBg else unselectedBg,
        animationSpec = tween(200),
        label = "tabBg"
      )
      val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(200),
        label = "tabText"
      )
      val borderColor by animateColorAsState(
        targetValue = if (isSelected) activeBorder else unselectedBorder,
        animationSpec = tween(200),
        label = "tabBorder"
      )

      Box(
        modifier = Modifier
          .height(40.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(bgColor)
          .border(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = borderColor,
            shape = RoundedCornerShape(12.dp)
          )
          .clickable { onWorkspaceSelected(workspace) }
          .padding(horizontal = 16.dp)
          .testTag("workspace_tab_${workspace.name.lowercase()}"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          if (isSelected) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(VibrantOrange)
            )
            Spacer(modifier = Modifier.width(7.dp))
          }

          Text(
            text = TaskoraStrings.workspace(workspace, language),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 13.sp
            )
          )
        }
      }
    }
  }
}
