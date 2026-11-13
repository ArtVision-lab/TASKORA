package com.example.data.model

enum class Priority {
  HIGH,
  MEDIUM,
  LOW
}

enum class WorkspaceType {
  ALL,
  PERSONAL,
  PROFESSIONAL,
  PROJECTS
}

enum class ProjectStatus {
  ON_TRACK,
  IN_PROGRESS,
  COMPLETED,
  PLANNING
}

enum class AppThemeMode {
  LIGHT,
  DARK,
  SYSTEM
}

enum class AppLanguage {
  EN,
  AR
}

data class TaskItem(
  val id: String,
  val title: String,
  val titleAr: String,
  val description: String = "",
  val descriptionAr: String = "",
  val priority: Priority = Priority.MEDIUM,
  val isCompleted: Boolean = false,
  val workspace: WorkspaceType = WorkspaceType.PERSONAL,
  val dueDateLabel: String = "Today",
  val dueDateLabelAr: String = "اليوم",
  val isToday: Boolean = true,
  val isOverdue: Boolean = false,
  val isUpcoming: Boolean = false,
  val projectName: String? = null,
  val estimatedMinutes: Int = 30
)

data class ProjectItem(
  val id: String,
  val name: String,
  val nameAr: String,
  val category: String,
  val categoryAr: String,
  val workspace: WorkspaceType = WorkspaceType.PROJECTS,
  val startDate: String,
  val dueDate: String,
  val totalTasks: Int,
  val completedTasks: Int,
  val status: ProjectStatus = ProjectStatus.ON_TRACK,
  val accentColor: Long = 0xFF174C3C
) {
  val progressPercent: Int
    get() = if (totalTasks > 0) ((completedTasks.toFloat() / totalTasks) * 100).toInt() else 0
}

data class CalendarItem(
  val id: String,
  val title: String,
  val titleAr: String,
  val time: String,
  val duration: String,
  val durationAr: String,
  val category: String,
  val categoryAr: String,
  val dayIndex: Int, // 0 = Mon ... 6 = Sun
  val isCompleted: Boolean = false
)
