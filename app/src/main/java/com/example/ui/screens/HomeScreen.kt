package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ProjectItem
import com.example.data.model.TaskItem
import com.example.data.model.WorkspaceType
import com.example.ui.ScreenDestination
import com.example.ui.components.ProjectCard
import com.example.ui.components.TaskCard
import com.example.ui.components.TaskoraHeaderBrand
import com.example.ui.components.WorkspaceSelectorTabs
import com.example.ui.localization.TaskoraStrings
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ripple
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import com.example.ui.theme.BrightEmeraldGreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkElevatedCard
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.SoftOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.VibrantOrange

@Composable
fun HomeScreen(
  todayTasks: List<TaskItem>,
  activeProjects: List<ProjectItem>,
  activeWorkspace: WorkspaceType,
  onWorkspaceSelected: (WorkspaceType) -> Unit,
  onToggleTask: (String) -> Unit,
  onAddTaskClick: () -> Unit,
  onNewProjectClick: () -> Unit,
  onPlanDayClick: () -> Unit,
  onNavigate: (ScreenDestination) -> Unit,
  todayTotalCount: Int,
  todayCompletedCount: Int,
  todayCompletionPercent: Int,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val isAr = language == AppLanguage.AR
  val isDark = MaterialTheme.colorScheme.background == DarkBackground
  val brightEmerald = if (isDark) BrightEmeraldGreen else EmeraldGreen
  val dateString = if (isAr) "الأربعاء، ١١ نوفمبر" else "Wednesday, Nov 11"
  val remainingCount = (todayTotalCount - todayCompletedCount).coerceAtLeast(0)

  val progressAnimation by animateFloatAsState(
    targetValue = (todayCompletionPercent / 100f).coerceIn(0f, 1f),
    animationSpec = tween(600),
    label = "dailyProgressAnim"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Top App Bar / Brand & Actions
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        TaskoraHeaderBrand(
          language = language,
          modifier = Modifier.weight(1f, fill = false)
        )

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          // Notification Button with subtle unread indicator dot
          Box(contentAlignment = Alignment.TopEnd) {
            IconButton(
              onClick = {
                // Toggle notification / show hint
              },
              modifier = Modifier.testTag("btn_notifications")
            ) {
              Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Box(
              modifier = Modifier
                .padding(top = 10.dp, end = 10.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(VibrantOrange)
            )
          }

          // Settings action
          IconButton(
            onClick = { onNavigate(ScreenDestination.SETTINGS) },
            modifier = Modifier.testTag("btn_settings_top")
          ) {
            Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = "Settings",
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // Greeting & Date Section
    item {
      Column {
        Text(
          text = dateString.uppercase(),
          style = MaterialTheme.typography.labelMedium.copy(
            letterSpacing = 1.2.sp,
            fontWeight = FontWeight.Bold
          ),
          color = EmeraldGreen
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = TaskoraStrings.greeting(TaskoraStrings.profileName(), language),
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.ExtraBold
          ),
          color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = TaskoraStrings.greetingSubtitle(todayTotalCount, language),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Workspace Selector
    item {
      WorkspaceSelectorTabs(
        selectedWorkspace = activeWorkspace,
        onWorkspaceSelected = onWorkspaceSelected,
        language = language
      )
    }

    // Daily Progress Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(
            Brush.linearGradient(
              colors = listOf(
                DeepForestGreen,
                EmeraldGreen
              )
            )
          )
          .padding(20.dp)
          .testTag("card_daily_progress")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = TaskoraStrings.dailyProgress(language),
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.2.sp
              ),
              color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              horizontalArrangement = Arrangement.spacedBy(16.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "$todayCompletedCount",
                  style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp
                  ),
                  color = Color.White
                )
                Text(
                  text = TaskoraStrings.completed(language),
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                  ),
                  color = Color.White.copy(alpha = 0.85f)
                )
              }

              // Subtle clean divider
              Box(
                modifier = Modifier
                  .height(34.dp)
                  .width(1.dp)
                  .background(Color.White.copy(alpha = 0.22f))
              )

              Column {
                Text(
                  text = "$remainingCount",
                  style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp
                  ),
                  color = Color.White
                )
                Text(
                  text = TaskoraStrings.remaining(language),
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                  ),
                  color = Color.White.copy(alpha = 0.85f)
                )
              }
            }
          }

          // Refined Progress percentage ring with transparent green track & vibrant orange indicator
          Box(
            modifier = Modifier.size(84.dp),
            contentAlignment = Alignment.Center
          ) {
            // Subtle transparent green progress track
            CircularProgressIndicator(
              progress = { 1f },
              modifier = Modifier.fillMaxSize(),
              color = EmeraldGreen.copy(alpha = 0.32f),
              strokeWidth = 8.dp
            )
            // Vibrant orange progress indicator
            CircularProgressIndicator(
              progress = { progressAnimation },
              modifier = Modifier.fillMaxSize(),
              color = VibrantOrange,
              strokeWidth = 8.dp,
              strokeCap = StrokeCap.Round
            )
            Text(
              text = "$todayCompletionPercent%",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                letterSpacing = (-0.5).sp
              ),
              color = Color.White
            )
          }
        }
      }
    }

    // Quick Action Buttons
    item {
      Column {
        Text(
          text = TaskoraStrings.quickActions(language),
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.2.sp
          ),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          QuickActionButton(
            label = TaskoraStrings.addTask(language),
            icon = Icons.Default.Add,
            onClick = onAddTaskClick,
            modifier = Modifier.weight(1f),
            testTag = "btn_quick_add_task",
            iconTint = brightEmerald,
            iconBg = EmeraldGreen.copy(alpha = if (isDark) 0.22f else 0.14f),
            accentDotColor = VibrantOrange
          )
          QuickActionButton(
            label = TaskoraStrings.newProject(language),
            icon = Icons.Default.CreateNewFolder,
            onClick = onNewProjectClick,
            modifier = Modifier.weight(1f),
            testTag = "btn_quick_new_project",
            iconTint = brightEmerald,
            iconBg = EmeraldGreen.copy(alpha = if (isDark) 0.22f else 0.14f),
            accentDotColor = SoftOrange
          )
          QuickActionButton(
            label = TaskoraStrings.planDay(language),
            icon = Icons.Default.CalendarToday,
            onClick = onPlanDayClick,
            modifier = Modifier.weight(1f),
            testTag = "btn_quick_plan_day",
            iconTint = VibrantOrange,
            iconBg = VibrantOrange.copy(alpha = if (isDark) 0.22f else 0.14f),
            accentDotColor = null
          )
        }
      }
    }



    // Today's Tasks Section Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = TaskoraStrings.todaysTasks(language),
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.width(6.dp))
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${todayTasks.size}",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldGreen
            )
          }
        }

        Text(
          text = TaskoraStrings.seeAll(language),
          style = MaterialTheme.typography.labelLarge,
          color = EmeraldGreen,
          modifier = Modifier
            .clickable { onNavigate(ScreenDestination.TASKS) }
            .testTag("btn_see_all_tasks")
        )
      }
    }

    // Today's Tasks List
    if (todayTasks.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = TaskoraStrings.emptyTasks(language),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    } else {
      items(todayTasks.take(4), key = { it.id }) { task ->
        TaskCard(
          task = task,
          onToggleComplete = { onToggleTask(task.id) },
          language = language
        )
      }
    }

    // Active Projects Section Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = TaskoraStrings.activeProjects(language),
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
          ),
          color = MaterialTheme.colorScheme.onSurface
        )

        Text(
          text = TaskoraStrings.seeAll(language),
          style = MaterialTheme.typography.labelLarge,
          color = EmeraldGreen,
          modifier = Modifier
            .clickable { onNavigate(ScreenDestination.PROJECTS) }
            .testTag("btn_see_all_projects")
        )
      }
    }

    // Active Projects List
    items(activeProjects.take(2), key = { it.id }) { project ->
      ProjectCard(
        project = project,
        language = language,
        compact = true
      )
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun QuickActionButton(
  label: String,
  icon: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  testTag: String,
  iconTint: Color = EmeraldGreen,
  iconBg: Color = EmeraldGreen.copy(alpha = 0.16f),
  accentDotColor: Color? = null
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by animateFloatAsState(
    targetValue = if (isPressed) 0.94f else 1.0f,
    animationSpec = tween(durationMillis = 100),
    label = "quickActionScale"
  )

  val isDark = MaterialTheme.colorScheme.background == DarkBackground
  val cardBg = if (isDark) DarkElevatedCard else MaterialTheme.colorScheme.surface
  val cardBorder = if (isPressed) EmeraldGreen else MaterialTheme.colorScheme.outline.copy(alpha = if (isDark) 0.6f else 0.8f)

  Box(
    modifier = modifier
      .graphicsLayer {
        scaleX = scale
        scaleY = scale
      }
      .clip(RoundedCornerShape(14.dp))
      .background(cardBg)
      .border(
        width = 1.dp,
        color = cardBorder,
        shape = RoundedCornerShape(14.dp)
      )
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(),
        onClick = onClick
      )
      .padding(vertical = 12.dp, horizontal = 6.dp)
      .testTag(testTag),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(iconBg),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = label,
          tint = iconTint,
          modifier = Modifier.size(22.dp)
        )
        if (accentDotColor != null) {
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(top = 2.dp, end = 2.dp)
              .size(6.dp)
              .clip(CircleShape)
              .background(accentDotColor)
          )
        }
      }
      Spacer(modifier = Modifier.height(7.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 11.5.sp
        ),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}
