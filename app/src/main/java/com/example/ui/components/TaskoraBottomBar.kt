package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.ScreenDestination
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.EmeraldGreen

data class NavItem(
  val destination: ScreenDestination,
  val label: (AppLanguage) -> String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val testTag: String
)

@Composable
fun TaskoraBottomBar(
  currentScreen: ScreenDestination,
  onNavigate: (ScreenDestination) -> Unit,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val items = listOf(
    NavItem(
      destination = ScreenDestination.HOME,
      label = { TaskoraStrings.navHome(it) },
      selectedIcon = Icons.Filled.Home,
      unselectedIcon = Icons.Outlined.Home,
      testTag = "nav_home"
    ),
    NavItem(
      destination = ScreenDestination.TASKS,
      label = { TaskoraStrings.navTasks(it) },
      selectedIcon = Icons.Filled.CheckCircle,
      unselectedIcon = Icons.Outlined.CheckCircle,
      testTag = "nav_tasks"
    ),
    NavItem(
      destination = ScreenDestination.PROJECTS,
      label = { TaskoraStrings.navProjects(it) },
      selectedIcon = Icons.Filled.Folder,
      unselectedIcon = Icons.Outlined.Folder,
      testTag = "nav_projects"
    ),
    NavItem(
      destination = ScreenDestination.CALENDAR,
      label = { TaskoraStrings.navCalendar(it) },
      selectedIcon = Icons.Filled.CalendarMonth,
      unselectedIcon = Icons.Outlined.CalendarMonth,
      testTag = "nav_calendar"
    )
  )

  NavigationBar(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 6.dp
  ) {
    items.forEach { item ->
      val isSelected = currentScreen == item.destination

      NavigationBarItem(
        selected = isSelected,
        onClick = { onNavigate(item.destination) },
        icon = {
          Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.label(language),
            modifier = Modifier.size(22.dp)
          )
        },
        label = {
          Text(
            text = item.label(language),
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = EmeraldGreen,
          selectedTextColor = EmeraldGreen,
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
          indicatorColor = EmeraldGreen.copy(alpha = 0.16f)
        ),
        modifier = Modifier.testTag(item.testTag)
      )
    }
  }
}
