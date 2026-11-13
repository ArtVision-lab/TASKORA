package com.example.data.repository

import android.content.Context
import com.example.data.model.AppLanguage
import com.example.data.model.AppThemeMode
import com.example.data.model.CalendarItem
import com.example.data.model.Priority
import com.example.data.model.ProjectItem
import com.example.data.model.ProjectStatus
import com.example.data.model.TaskItem
import com.example.data.model.WorkspaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class TaskoraRepository(context: Context) {

  private val prefs = context.getSharedPreferences("taskora_prefs", Context.MODE_PRIVATE)

  private val _themeMode = MutableStateFlow(
    when (prefs.getString("theme_mode", "SYSTEM")) {
      "LIGHT" -> AppThemeMode.LIGHT
      "DARK" -> AppThemeMode.DARK
      else -> AppThemeMode.SYSTEM
    }
  )
  val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

  private val _language = MutableStateFlow(
    when (prefs.getString("app_language", "EN")) {
      "AR" -> AppLanguage.AR
      else -> AppLanguage.EN
    }
  )
  val language: StateFlow<AppLanguage> = _language.asStateFlow()

  private val _activeWorkspace = MutableStateFlow(WorkspaceType.ALL)
  val activeWorkspace: StateFlow<WorkspaceType> = _activeWorkspace.asStateFlow()

  private val _notificationsEnabled = MutableStateFlow(
    prefs.getBoolean("notifications_enabled", true)
  )
  val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

  private val _tasks = MutableStateFlow(createInitialTasks())
  val tasks: StateFlow<List<TaskItem>> = _tasks.asStateFlow()

  private val _projects = MutableStateFlow(createInitialProjects())
  val projects: StateFlow<List<ProjectItem>> = _projects.asStateFlow()

  private val _calendarItems = MutableStateFlow(createInitialCalendarItems())
  val calendarItems: StateFlow<List<CalendarItem>> = _calendarItems.asStateFlow()

  // Future AI Integration Slot:
  // AI assistant services can be plugged into this repository layer without altering data models.

  fun setThemeMode(mode: AppThemeMode) {
    _themeMode.value = mode
    prefs.edit().putString("theme_mode", mode.name).apply()
  }

  fun setLanguage(lang: AppLanguage) {
    _language.value = lang
    prefs.edit().putString("app_language", lang.name).apply()
  }

  fun setWorkspace(workspace: WorkspaceType) {
    _activeWorkspace.value = workspace
  }

  fun setNotificationsEnabled(enabled: Boolean) {
    _notificationsEnabled.value = enabled
    prefs.edit().putBoolean("notifications_enabled", enabled).apply()
  }

  fun toggleTaskCompletion(taskId: String) {
    _tasks.update { list ->
      list.map { task ->
        if (task.id == taskId) {
          val updated = task.copy(isCompleted = !task.isCompleted)
          // Also update associated project completion if linked
          task.projectName?.let { pName ->
            updateProjectTaskCount(pName, if (updated.isCompleted) 1 else -1)
          }
          updated
        } else {
          task
        }
      }
    }
  }

  private fun updateProjectTaskCount(projectName: String, delta: Int) {
    _projects.update { list ->
      list.map { project ->
        if (project.name.equals(projectName, ignoreCase = true) || project.nameAr.contains(projectName)) {
          val newCompleted = (project.completedTasks + delta).coerceIn(0, project.totalTasks)
          project.copy(completedTasks = newCompleted)
        } else {
          project
        }
      }
    }
  }

  fun addTask(
    title: String,
    titleAr: String,
    description: String,
    descriptionAr: String,
    priority: Priority,
    workspace: WorkspaceType,
    projectName: String? = null
  ) {
    val newTask = TaskItem(
      id = UUID.randomUUID().toString(),
      title = title,
      titleAr = if (titleAr.isNotBlank()) titleAr else title,
      description = description,
      descriptionAr = if (descriptionAr.isNotBlank()) descriptionAr else description,
      priority = priority,
      isCompleted = false,
      workspace = workspace,
      dueDateLabel = "Today",
      dueDateLabelAr = "اليوم",
      isToday = true,
      projectName = projectName
    )
    _tasks.update { listOf(newTask) + it }
  }

  fun addProject(
    name: String,
    nameAr: String,
    category: String,
    categoryAr: String,
    workspace: WorkspaceType,
    dueDate: String
  ) {
    val newProject = ProjectItem(
      id = UUID.randomUUID().toString(),
      name = name,
      nameAr = if (nameAr.isNotBlank()) nameAr else name,
      category = category,
      categoryAr = if (categoryAr.isNotBlank()) categoryAr else category,
      workspace = workspace,
      startDate = "Nov 11",
      dueDate = dueDate,
      totalTasks = 5,
      completedTasks = 0,
      status = ProjectStatus.IN_PROGRESS,
      accentColor = 0xFF174C3C
    )
    _projects.update { listOf(newProject) + it }
  }

  }

  private fun createInitialTasks(): List<TaskItem> {
    return listOf(
      TaskItem(
        id = "t1",
        title = "Review Q4 product roadmap & OKRs",
        titleAr = "مراجعة خريطة طريق الربع الرابع والأهداف",
        description = "Align engineering milestones with design sprint goals",
        descriptionAr = "مواءمة مراحل التطوير مع أهداف جولة التصميم",
        priority = Priority.HIGH,
        isCompleted = false,
        workspace = WorkspaceType.PROFESSIONAL,
        dueDateLabel = "Today, 11:00 AM",
        dueDateLabelAr = "اليوم، 11:00 ص",
        isToday = true,
        projectName = "Taskora Mobile App v1.0",
        estimatedMinutes = 60
      ),
      TaskItem(
        id = "t2",
        title = "Prepare slide deck for leadership sync",
        titleAr = "إعداد العرض التقديمي للاجتماع القيادي",
        description = "Include metrics, deliverables, and team bandwidth",
        descriptionAr = "تضمين المؤشرات، المخرجات، وقدرة استيعاب الفريق",
        priority = Priority.HIGH,
        isCompleted = true,
        workspace = WorkspaceType.PROFESSIONAL,
        dueDateLabel = "Today, 02:00 PM",
        dueDateLabelAr = "اليوم، 02:00 م",
        isToday = true,
        projectName = "Annual Performance & Strategy Review",
        estimatedMinutes = 45
      ),
      TaskItem(
        id = "t3",
        title = "Complete deep-work focus block (2h)",
        titleAr = "إتمام جلسة عمل تركيز عميق (ساعتان)",
        description = "Audit core user flows and polish tactile interactions",
        descriptionAr = "تدقيق تجربة المستخدم وتحسين الاستجابة والتفاعل",
        priority = Priority.MEDIUM,
        isCompleted = false,
        workspace = WorkspaceType.PERSONAL,
        dueDateLabel = "Today, 04:30 PM",
        dueDateLabelAr = "اليوم، 04:30 م",
        isToday = true,
        projectName = null,
        estimatedMinutes = 120
      ),
      TaskItem(
        id = "t4",
        title = "Review Figma design system tokens",
        titleAr = "مراجعة مكونات نظام التصميم في فيجما",
        description = "Verify dark mode contrast tokens and typography scales",
        descriptionAr = "التحقق من تباين الوضع الداكن ومقاييس الخطوط",
        priority = Priority.MEDIUM,
        isCompleted = false,
        workspace = WorkspaceType.PROJECTS,
        dueDateLabel = "Today, 06:00 PM",
        dueDateLabelAr = "اليوم، 06:00 م",
        isToday = true,
        projectName = "Brand Identity & Design System",
        estimatedMinutes = 40
      ),
      TaskItem(
        id = "t5",
        title = "Draft monthly budget and financial goals",
        titleAr = "إعداد الميزانية الشهرية وتحديد أهداف الادخار",
        description = "Review subscriptions and quarterly savings trajectory",
        descriptionAr = "مراجعة الاشتراكات ومسار المدخرات الربع سنوية",
        priority = Priority.LOW,
        isCompleted = false,
        workspace = WorkspaceType.PERSONAL,
        dueDateLabel = "Tomorrow",
        dueDateLabelAr = "غداً",
        isToday = false,
        isUpcoming = true,
        projectName = null,
        estimatedMinutes = 30
      ),
      TaskItem(
        id = "t6",
        title = "Team 1-on-1 weekly summaries",
        titleAr = "ملخصات جلسات المتابعة الفردية الأسبوعية",
        description = "Share action items and follow up on blockers",
        descriptionAr = "مشاركة بنود العمل ومتابعة المعوقات",
        priority = Priority.MEDIUM,
        isCompleted = false,
        workspace = WorkspaceType.PROFESSIONAL,
        dueDateLabel = "Yesterday",
        dueDateLabelAr = "أمس",
        isToday = false,
        isOverdue = true,
        projectName = "Annual Performance & Strategy Review",
        estimatedMinutes = 35
      ),
      TaskItem(
        id = "t7",
        title = "Finalize architectural specs for mobile API",
        titleAr = "اعتماد المواصفات المعمارية لواجهة البرمجة",
        description = "Document request schemas and error payloads",
        descriptionAr = "توثيق مخططات الطلبات وهيكل رسائل الخطأ",
        priority = Priority.HIGH,
        isCompleted = false,
        workspace = WorkspaceType.PROJECTS,
        dueDateLabel = "In 2 days",
        dueDateLabelAr = "خلال يومين",
        isToday = false,
        isUpcoming = true,
        projectName = "Taskora Mobile App v1.0",
        estimatedMinutes = 90
      )
    )
  }

  private fun createInitialProjects(): List<ProjectItem> {
    return listOf(
      ProjectItem(
        id = "p1",
        name = "Taskora Mobile App v1.0",
        nameAr = "تطبيق تاسكورا للهواتف الإصدار 1.0",
        category = "Mobile Architecture",
        categoryAr = "تطوير تطبيقات الجوال",
        workspace = WorkspaceType.PROJECTS,
        startDate = "Oct 15",
        dueDate = "Nov 28",
        totalTasks = 8,
        completedTasks = 6,
        status = ProjectStatus.ON_TRACK,
        accentColor = 0xFF174C3C
      ),
      ProjectItem(
        id = "p2",
        name = "Brand Identity & Design System",
        nameAr = "الهوية البصرية ونظام التصميم",
        category = "Design & UX",
        categoryAr = "التصميم وتجربة المستخدم",
        workspace = WorkspaceType.PROFESSIONAL,
        startDate = "Nov 01",
        dueDate = "Dec 05",
        totalTasks = 12,
        completedTasks = 9,
        status = ProjectStatus.IN_PROGRESS,
        accentColor = 0xFFFF8A35
      ),
      ProjectItem(
        id = "p3",
        name = "Annual Strategy & Performance Review",
        nameAr = "مراجعة الأداء والاستراتيجية السنوية",
        category = "Operations",
        categoryAr = "العمليات والإدارة",
        workspace = WorkspaceType.PROFESSIONAL,
        startDate = "Nov 05",
        dueDate = "Dec 15",
        totalTasks = 5,
        completedTasks = 3,
        status = ProjectStatus.ON_TRACK,
        accentColor = 0xFF267653
      ),
      ProjectItem(
        id = "p4",
        name = "Personal Wellness & Marathon Training",
        nameAr = "اللياقة الشخصية وتدريب الماراثون",
        category = "Personal Growth",
        categoryAr = "تطوير الذات والصحة",
        workspace = WorkspaceType.PERSONAL,
        startDate = "Nov 01",
        dueDate = "Jan 10",
        totalTasks = 10,
        completedTasks = 4,
        status = ProjectStatus.PLANNING,
        accentColor = 0xFFFFB36B
      )
    )
  }

  private fun createInitialCalendarItems(): List<CalendarItem> {
    return listOf(
      CalendarItem(
        id = "c1",
        title = "Sprint Planning & Backlog Grooming",
        titleAr = "تخطيط الدورة البرمجية وترتيب المهام",
        time = "09:30 AM",
        duration = "45m",
        durationAr = "45 د",
        category = "Meeting",
        categoryAr = "اجتماع",
        dayIndex = 2, // Wednesday
        isCompleted = true
      ),
      CalendarItem(
        id = "c2",
        title = "Review Q4 product roadmap & OKRs",
        titleAr = "مراجعة خريطة طريق الربع الرابع والأهداف",
        time = "11:00 AM",
        duration = "1h",
        durationAr = "1 س",
        category = "High Priority Task",
        categoryAr = "مهمة ذات أولوية",
        dayIndex = 2,
        isCompleted = false
      ),
      CalendarItem(
        id = "c3",
        title = "Leadership Sync & Weekly Metrics",
        titleAr = "الاجتماع القيادي ومؤشرات الأداء",
        time = "02:00 PM",
        duration = "30m",
        durationAr = "30 د",
        category = "Presentation",
        categoryAr = "عرض تقديمي",
        dayIndex = 2,
        isCompleted = false
      ),
      CalendarItem(
        id = "c4",
        title = "Deep Focus: Design Tokens Inspection",
        titleAr = "تركيز عميق: فحص عناصر نظام التصميم",
        time = "04:00 PM",
        duration = "1h 30m",
        durationAr = "1 س 30 د",
        category = "Focus Block",
        categoryAr = "جلسة تركيز",
        dayIndex = 2,
        isCompleted = false
      ),
      CalendarItem(
        id = "c5",
        title = "Architecture Review: Cloud Sync Engine",
        titleAr = "مراجعة البنية المعمارية: محرك المزامنة",
        time = "10:00 AM",
        duration = "1h",
        durationAr = "1 س",
        category = "Architecture",
        categoryAr = "بنية تحتية",
        dayIndex = 3, // Thursday
        isCompleted = false
      ),
      CalendarItem(
        id = "c6",
        title = "Personal Reading & Weekend Planning",
        titleAr = "قراءة شخصية وتخطيط عطلة نهاية الأسبوع",
        time = "05:00 PM",
        duration = "45m",
        durationAr = "45 د",
        category = "Personal",
        categoryAr = "شخصي",
        dayIndex = 4, // Friday
        isCompleted = false
      )
    )
  }

  }
}
