package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AppLanguage
import com.example.data.model.AppThemeMode
import com.example.data.model.Priority
import com.example.data.model.WorkspaceType
import com.example.data.repository.TaskoraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ScreenDestination {
  SPLASH,
  HOME,
  TASKS,
  PROJECTS,
  CALENDAR,
  SETTINGS
}

enum class TaskFilter {
  ALL,
  TODAY,
  UPCOMING,
  COMPLETED,
  OVERDUE
}

class TaskoraViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = TaskoraRepository(application)

  val themeMode = repository.themeMode
  val language = repository.language
  val activeWorkspace = repository.activeWorkspace
  val notificationsEnabled = repository.notificationsEnabled
  val allTasks = repository.tasks
  val allProjects = repository.projects
  val calendarItems = repository.calendarItems

  private val _currentScreen = MutableStateFlow(ScreenDestination.SPLASH)
  val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

  private val _showSplash = MutableStateFlow(true)
  val showSplash: StateFlow<Boolean> = _showSplash.asStateFlow()

  private val _taskFilter = MutableStateFlow(TaskFilter.TODAY)
  val taskFilter: StateFlow<TaskFilter> = _taskFilter.asStateFlow()

  private val _priorityFilter = MutableStateFlow<Priority?>(null)
  val priorityFilter: StateFlow<Priority?> = _priorityFilter.asStateFlow()

  private val _selectedDayIndex = MutableStateFlow(2) // Wednesday
  val selectedDayIndex: StateFlow<Int> = _selectedDayIndex.asStateFlow()

  private val _showCreateTaskDialog = MutableStateFlow(false)
  val showCreateTaskDialog: StateFlow<Boolean> = _showCreateTaskDialog.asStateFlow()

  private val _showCreateProjectDialog = MutableStateFlow(false)
  val showCreateProjectDialog: StateFlow<Boolean> = _showCreateProjectDialog.asStateFlow()

  private val _snackbarMessage = MutableStateFlow<String?>(null)
  val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

  // Dynamic daily progress calculations
  val todayTasks = combine(allTasks, activeWorkspace) { tasks, ws ->
    tasks.filter { task ->
      task.isToday && (ws == WorkspaceType.ALL || task.workspace == ws)
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val todayTotalCount = combine(todayTasks) { it.first().size }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val todayCompletedCount = combine(todayTasks) { tasks ->
    tasks.first().count { it.isCompleted }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val todayCompletionPercent = combine(todayTotalCount, todayCompletedCount) { total, done ->
    if (total > 0) ((done.toFloat() / total) * 100).toInt() else 0
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  // Filtered tasks for the Tasks Screen
  val filteredTasks = combine(
    allTasks,
    activeWorkspace,
    _taskFilter,
    _priorityFilter
  ) { tasks, ws, filter, priority ->
    tasks.filter { task ->
      val matchesWorkspace = ws == WorkspaceType.ALL || task.workspace == ws
      val matchesFilter = when (filter) {
        TaskFilter.ALL -> true
        TaskFilter.TODAY -> task.isToday
        TaskFilter.UPCOMING -> task.isUpcoming
        TaskFilter.COMPLETED -> task.isCompleted
        TaskFilter.OVERDUE -> task.isOverdue && !task.isCompleted
      }
      val matchesPriority = priority == null || task.priority == priority
      matchesWorkspace && matchesFilter && matchesPriority
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Filtered projects for the Projects Screen
  val filteredProjects = combine(allProjects, activeWorkspace) { projects, ws ->
    projects.filter { project ->
      ws == WorkspaceType.ALL || project.workspace == ws
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun completeSplash() {
    _showSplash.value = false
    _currentScreen.value = ScreenDestination.HOME
  }

  fun replaySplash() {
    _showSplash.value = true
    _currentScreen.value = ScreenDestination.SPLASH
  }

  fun dismissSplash() {
    completeSplash()
  }

  fun navigateTo(destination: ScreenDestination) {
    _currentScreen.value = destination
  }

  fun setWorkspace(workspace: WorkspaceType) {
    repository.setWorkspace(workspace)
  }

  fun setTaskFilter(filter: TaskFilter) {
    _taskFilter.value = filter
  }

  fun setPriorityFilter(priority: Priority?) {
    _priorityFilter.value = priority
  }

  fun setSelectedDayIndex(index: Int) {
    _selectedDayIndex.value = index
  }

  fun toggleTask(taskId: String) {
    repository.toggleTaskCompletion(taskId)
  }

  fun setThemeMode(mode: AppThemeMode) {
    repository.setThemeMode(mode)
  }

  fun setLanguage(lang: AppLanguage) {
    repository.setLanguage(lang)
  }

  fun setNotificationsEnabled(enabled: Boolean) {
    repository.setNotificationsEnabled(enabled)
  }

  fun openCreateTaskDialog() {
    _showCreateTaskDialog.value = true
  }

  fun closeCreateTaskDialog() {
    _showCreateTaskDialog.value = false
  }

  fun openCreateProjectDialog() {
    _showCreateProjectDialog.value = true
  }

  fun closeCreateProjectDialog() {
    _showCreateProjectDialog.value = false
  }

  fun createTask(
    title: String,
    titleAr: String,
    description: String,
    descriptionAr: String,
    priority: Priority,
    workspace: WorkspaceType,
    projectName: String? = null
  ) {
    repository.addTask(
      title = title,
      titleAr = titleAr,
      description = description,
      descriptionAr = descriptionAr,
      priority = priority,
      workspace = workspace,
      projectName = projectName
    )
    closeCreateTaskDialog()
    showToast(if (language.value == AppLanguage.EN) "Task added successfully" else "تمت إضافة المهمة بنجاح")
  }

  fun createProject(
    name: String,
    nameAr: String,
    category: String,
    categoryAr: String,
    workspace: WorkspaceType,
    dueDate: String
  ) {
    repository.addProject(
      name = name,
      nameAr = nameAr,
      category = category,
      categoryAr = categoryAr,
      workspace = workspace,
      dueDate = dueDate
    )
    closeCreateProjectDialog()
    showToast(if (language.value == AppLanguage.EN) "Project created successfully" else "تم إنشاء المشروع بنجاح")
  }


  fun showToast(message: String) {
    viewModelScope.launch {
      _snackbarMessage.value = message
      kotlinx.coroutines.delay(2500)
      if (_snackbarMessage.value == message) {
        _snackbarMessage.value = null
      }
    }
  }

  fun clearSnackbar() {
    _snackbarMessage.value = null
  }
}
