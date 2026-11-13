package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AppLanguage
import com.example.ui.ScreenDestination
import com.example.ui.TaskoraViewModel
import com.example.ui.components.CreateProjectDialog
import com.example.ui.components.CreateTaskDialog
import com.example.ui.components.TaskoraBottomBar
import com.example.ui.screens.CalendarScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProjectsScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TasksScreen
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.TaskoraTheme

class MainActivity : ComponentActivity() {

  private val viewModel: TaskoraViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
      val language by viewModel.language.collectAsStateWithLifecycle()
      val showSplash by viewModel.showSplash.collectAsStateWithLifecycle()
      val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
      val activeWorkspace by viewModel.activeWorkspace.collectAsStateWithLifecycle()
      val notificationsEnabled by viewModel.notificationsEnabled.collectAsStateWithLifecycle()

      val todayTasks by viewModel.todayTasks.collectAsStateWithLifecycle()
      val filteredTasks by viewModel.filteredTasks.collectAsStateWithLifecycle()
      val filteredProjects by viewModel.filteredProjects.collectAsStateWithLifecycle()
      val calendarItems by viewModel.calendarItems.collectAsStateWithLifecycle()

      val taskFilter by viewModel.taskFilter.collectAsStateWithLifecycle()
      val priorityFilter by viewModel.priorityFilter.collectAsStateWithLifecycle()
      val selectedDayIndex by viewModel.selectedDayIndex.collectAsStateWithLifecycle()

      val todayTotalCount by viewModel.todayTotalCount.collectAsStateWithLifecycle()
      val todayCompletedCount by viewModel.todayCompletedCount.collectAsStateWithLifecycle()
      val todayCompletionPercent by viewModel.todayCompletionPercent.collectAsStateWithLifecycle()

      val showCreateTaskDialog by viewModel.showCreateTaskDialog.collectAsStateWithLifecycle()
      val showCreateProjectDialog by viewModel.showCreateProjectDialog.collectAsStateWithLifecycle()
      val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

      val isArabic = language == AppLanguage.AR
      val layoutDirection = if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr

      // Handle back button: navigate back to Home if in subscreen (Splash cannot be returned to)
      BackHandler(enabled = currentScreen != ScreenDestination.SPLASH && currentScreen != ScreenDestination.HOME) {
        viewModel.navigateTo(ScreenDestination.HOME)
      }

      CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        TaskoraTheme(themeMode = themeMode, isArabic = isArabic) {
          Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
              if (currentScreen != ScreenDestination.SPLASH && currentScreen != ScreenDestination.SETTINGS) {
                TaskoraBottomBar(
                  currentScreen = currentScreen,
                  onNavigate = viewModel::navigateTo,
                  language = language,
                  modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                )
              }
            }
          ) { innerPadding ->
            Box(
              modifier = Modifier
                .fillMaxSize()
                .padding(if (currentScreen == ScreenDestination.SPLASH) androidx.compose.foundation.layout.PaddingValues(0.dp) else innerPadding)
            ) {
              AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                  if (initialState == ScreenDestination.SPLASH) {
                    fadeIn(animationSpec = androidx.compose.animation.core.tween(400)) togetherWith
                      fadeOut(animationSpec = androidx.compose.animation.core.tween(400))
                  } else {
                    fadeIn(animationSpec = androidx.compose.animation.core.tween(220)) togetherWith
                      fadeOut(animationSpec = androidx.compose.animation.core.tween(220))
                  }
                },
                label = "screenTransition"
              ) { screen ->
                when (screen) {
                  ScreenDestination.SPLASH -> {
                    SplashScreen(
                      onDismiss = viewModel::completeSplash,
                      language = language
                    )
                  }

                  ScreenDestination.HOME -> {
                      HomeScreen(
                        todayTasks = todayTasks,
                        activeProjects = filteredProjects,
                        activeWorkspace = activeWorkspace,
                        onWorkspaceSelected = viewModel::setWorkspace,
                        onToggleTask = viewModel::toggleTask,
                        onAddTaskClick = viewModel::openCreateTaskDialog,
                        onNewProjectClick = viewModel::openCreateProjectDialog,
                        onPlanDayClick = { viewModel.navigateTo(ScreenDestination.CALENDAR) },
                        onNavigate = viewModel::navigateTo,
                        todayTotalCount = todayTotalCount,
                        todayCompletedCount = todayCompletedCount,
                        todayCompletionPercent = todayCompletionPercent,
                        language = language
                      )
                    }

                    ScreenDestination.TASKS -> {
                      TasksScreen(
                        tasks = filteredTasks,
                        activeWorkspace = activeWorkspace,
                        onWorkspaceSelected = viewModel::setWorkspace,
                        activeFilter = taskFilter,
                        onFilterSelected = viewModel::setTaskFilter,
                        activePriority = priorityFilter,
                        onPrioritySelected = viewModel::setPriorityFilter,
                        onToggleTask = viewModel::toggleTask,
                        onAddTaskClick = viewModel::openCreateTaskDialog,
                        language = language
                      )
                    }

                    ScreenDestination.PROJECTS -> {
                      ProjectsScreen(
                        projects = filteredProjects,
                        activeWorkspace = activeWorkspace,
                        onWorkspaceSelected = viewModel::setWorkspace,
                        onNewProjectClick = viewModel::openCreateProjectDialog,
                        language = language
                      )
                    }

                    ScreenDestination.CALENDAR -> {
                      CalendarScreen(
                        calendarItems = calendarItems,
                        selectedDayIndex = selectedDayIndex,
                        onDaySelected = viewModel::setSelectedDayIndex,
                        language = language
                      )
                    }

                    ScreenDestination.SETTINGS -> {
                      SettingsScreen(
                        currentTheme = themeMode,
                        onThemeChanged = viewModel::setThemeMode,
                        currentLanguage = language,
                        onLanguageChanged = viewModel::setLanguage,
                        notificationsEnabled = notificationsEnabled,
                        onNotificationsToggled = viewModel::setNotificationsEnabled,
                        onBackClick = { viewModel.navigateTo(ScreenDestination.HOME) },
                        onReplaySplash = { viewModel.replaySplash() }
                      )
                    }
                  }
                }

                // Custom subtle toast notification banner
                AnimatedVisibility(
                  visible = snackbarMessage != null,
                  enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                  exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                  modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp)
                ) {
                  snackbarMessage?.let { msg ->
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(DeepForestGreen)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .testTag("app_snackbar")
                    ) {
                      Text(
                        text = msg,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                      )
                    }
                  }
                }
              }
            }

          // Creation Dialogs
          if (showCreateTaskDialog) {
            CreateTaskDialog(
              onDismiss = viewModel::closeCreateTaskDialog,
              onConfirm = { title, titleAr, desc, descAr, priority, ws ->
                viewModel.createTask(title, titleAr, desc, descAr, priority, ws)
              },
              language = language
            )
          }

          if (showCreateProjectDialog) {
            CreateProjectDialog(
              onDismiss = viewModel::closeCreateProjectDialog,
              onConfirm = { name, nameAr, cat, catAr, ws, dueDate ->
                viewModel.createProject(name, nameAr, cat, catAr, ws, dueDate)
              },
              language = language
            )
          }
        }
      }
    }
  }
}
