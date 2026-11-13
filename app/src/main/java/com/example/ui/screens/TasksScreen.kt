package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.data.model.Priority
import com.example.data.model.TaskItem
import com.example.data.model.WorkspaceType
import com.example.ui.TaskFilter
import com.example.ui.components.TaskCard
import com.example.ui.components.WorkspaceSelectorTabs
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkElevatedCard
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.ErrorCoral
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.VibrantOrange
import com.example.ui.theme.WarningAmber

@Composable
fun TasksScreen(
  tasks: List<TaskItem>,
  activeWorkspace: WorkspaceType,
  onWorkspaceSelected: (WorkspaceType) -> Unit,
  activeFilter: TaskFilter,
  onFilterSelected: (TaskFilter) -> Unit,
  activePriority: Priority?,
  onPrioritySelected: (Priority?) -> Unit,
  onToggleTask: (String) -> Unit,
  onAddTaskClick: () -> Unit,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val filters = listOf(
    TaskFilter.ALL to TaskoraStrings.filterAll(language),
    TaskFilter.TODAY to TaskoraStrings.filterToday(language),
    TaskFilter.UPCOMING to TaskoraStrings.filterUpcoming(language),
    TaskFilter.COMPLETED to TaskoraStrings.filterCompleted(language),
    TaskFilter.OVERDUE to TaskoraStrings.filterOverdue(language)
  )

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = Color.Transparent,
    floatingActionButton = {
      FloatingActionButton(
        onClick = onAddTaskClick,
        containerColor = DeepForestGreen,
        contentColor = Color.White,
        shape = CircleShape,
        modifier = Modifier.testTag("fab_add_task")
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = TaskoraStrings.addTask(language),
          modifier = Modifier.size(24.dp)
        )
      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      item {
        Spacer(modifier = Modifier.height(8.dp))
        // Screen Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = TaskoraStrings.navTasks(language),
              style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold
              ),
              color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "${tasks.size} ${if (language == AppLanguage.AR) "مهام معروضة" else "tasks shown"}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      // Workspace selector tabs
      item {
        WorkspaceSelectorTabs(
          selectedWorkspace = activeWorkspace,
          onWorkspaceSelected = onWorkspaceSelected,
          language = language
        )
      }

      // Filter tabs: All, Today, Upcoming, Completed, Overdue
      item {
        val isDark = MaterialTheme.colorScheme.background == DarkBackground
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          filters.forEach { (filter, label) ->
            val isSelected = filter == activeFilter
            val activeBg = if (isDark) EmeraldGreen else DeepForestGreen
            val unselectedBg = if (isDark) DarkElevatedCard else MaterialTheme.colorScheme.surface

            val bgColor by animateColorAsState(
              targetValue = if (isSelected) activeBg else unselectedBg,
              label = "filterBg"
            )
            val textColor by animateColorAsState(
              targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
              label = "filterText"
            )
            val borderColor by animateColorAsState(
              targetValue = if (isSelected) (if (isDark) EmeraldGreen else DeepForestGreen) else MaterialTheme.colorScheme.outline,
              label = "filterBorder"
            )

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(bgColor)
                .border(
                  width = 1.dp,
                  color = borderColor,
                  shape = RoundedCornerShape(10.dp)
                )
                .clickable { onFilterSelected(filter) }
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .testTag("filter_tab_${filter.name.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = label,
                color = textColor,
                fontSize = 12.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        }
      }

      // Priority Filter Chips
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${TaskoraStrings.selectPriority(language)}:",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          // "All" chip
          val allSelected = activePriority == null
          PriorityChip(
            label = TaskoraStrings.filterAll(language),
            isSelected = allSelected,
            color = DeepForestGreen,
            onClick = { onPrioritySelected(null) },
            testTag = "priority_chip_all"
          )

          PriorityChip(
            label = TaskoraStrings.priorityLabel(Priority.HIGH, language),
            isSelected = activePriority == Priority.HIGH,
            color = VibrantOrange,
            onClick = { onPrioritySelected(Priority.HIGH) },
            testTag = "priority_chip_high"
          )

          PriorityChip(
            label = TaskoraStrings.priorityLabel(Priority.MEDIUM, language),
            isSelected = activePriority == Priority.MEDIUM,
            color = WarningAmber,
            onClick = { onPrioritySelected(Priority.MEDIUM) },
            testTag = "priority_chip_medium"
          )

          PriorityChip(
            label = TaskoraStrings.priorityLabel(Priority.LOW, language),
            isSelected = activePriority == Priority.LOW,
            color = SuccessGreen,
            onClick = { onPrioritySelected(Priority.LOW) },
            testTag = "priority_chip_low"
          )
        }
      }

      // Tasks List or Empty State
      if (tasks.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(20.dp))
              .background(MaterialTheme.colorScheme.surface)
              .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp)
              )
              .padding(40.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = EmeraldGreen.copy(alpha = 0.5f),
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = TaskoraStrings.emptyTasks(language),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      } else {
        items(tasks, key = { it.id }) { task ->
          TaskCard(
            task = task,
            onToggleComplete = { onToggleTask(task.id) },
            language = language
          )
        }
      }

      item {
        Spacer(modifier = Modifier.height(72.dp))
      }
    }
  }
}

@Composable
fun PriorityChip(
  label: String,
  isSelected: Boolean,
  color: Color,
  onClick: () -> Unit,
  testTag: String
) {
  val isDark = MaterialTheme.colorScheme.background == DarkBackground
  val chipBg = if (isSelected) {
    if (isDark) color.copy(alpha = 0.25f) else color.copy(alpha = 0.16f)
  } else {
    if (isDark) DarkElevatedCard else MaterialTheme.colorScheme.surface
  }
  val chipBorder = if (isSelected) color else MaterialTheme.colorScheme.outline
  val chipTextColor = if (isSelected) {
    if (isDark) Color.White else Color(0xFF13221A) // Charcoal on tinted background for 12:1 WCAG AAA contrast
  } else {
    MaterialTheme.colorScheme.onSurfaceVariant
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(chipBg)
      .border(
        width = 1.dp,
        color = chipBorder,
        shape = RoundedCornerShape(8.dp)
      )
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 5.dp)
      .testTag(testTag)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      if (isSelected) {
        Box(
          modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(color)
        )
        Spacer(modifier = Modifier.width(5.dp))
      }
      Text(
        text = label,
        fontSize = 11.5.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = chipTextColor
      )
    }
  }
}
