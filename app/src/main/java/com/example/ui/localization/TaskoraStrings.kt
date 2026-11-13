package com.example.ui.localization

import com.example.data.model.AppLanguage
import com.example.data.model.Priority
import com.example.data.model.ProjectStatus
import com.example.data.model.WorkspaceType

object TaskoraStrings {

  fun appName(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "TASKORA"
    AppLanguage.AR -> "تاسكورا"
  }

  fun tagline(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Organize Your Life. Achieve More."
    AppLanguage.AR -> "نظّم حياتك... وأنجز المزيد."
  }

  // Navigation
  fun navHome(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Home"
    AppLanguage.AR -> "الرئيسية"
  }

  fun navTasks(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Tasks"
    AppLanguage.AR -> "المهام"
  }

  fun navProjects(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Projects"
    AppLanguage.AR -> "المشاريع"
  }

  fun navCalendar(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Calendar"
    AppLanguage.AR -> "التقويم"
  }

  // Workspaces
  fun workspace(type: WorkspaceType, lang: AppLanguage) = when (type) {
    WorkspaceType.ALL -> if (lang == AppLanguage.EN) "All Workspaces" else "كل المساحات"
    WorkspaceType.PERSONAL -> if (lang == AppLanguage.EN) "Personal" else "شخصي"
    WorkspaceType.PROFESSIONAL -> if (lang == AppLanguage.EN) "Professional" else "مهني"
    WorkspaceType.PROJECTS -> if (lang == AppLanguage.EN) "Projects" else "مشاريع"
  }

  // Home Greeting
  fun greeting(name: String, lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Good morning, $name"
    AppLanguage.AR -> "صباح الخير، $name"
  }

  fun greetingSubtitle(count: Int, lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "You have $count tasks scheduled for today."
    AppLanguage.AR -> "لديك $count مهام مجدولة لليوم."
  }

  // Cards
  fun dailyProgress(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Daily Progress"
    AppLanguage.AR -> "الإنجاز اليومي"
  }

  fun completed(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Completed"
    AppLanguage.AR -> "مكتملة"
  }

  fun remaining(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Remaining"
    AppLanguage.AR -> "متبقية"
  }

  fun quickActions(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Quick Actions"
    AppLanguage.AR -> "إجراءات سريعة"
  }

  fun addTask(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Add Task"
    AppLanguage.AR -> "إضافة مهمة"
  }

  fun newProject(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "New Project"
    AppLanguage.AR -> "مشروع جديد"
  }

  fun planDay(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Plan Day"
    AppLanguage.AR -> "تخطيط اليوم"
  }

  fun todaysTasks(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Today's Tasks"
    AppLanguage.AR -> "مهام اليوم"
  }

  fun activeProjects(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Active Projects"
    AppLanguage.AR -> "المشاريع النشطة"
  }

  fun seeAll(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "See all"
    AppLanguage.AR -> "عرض الكل"
  }

  // Tasks screen filters
  fun filterAll(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "All"
    AppLanguage.AR -> "الكل"
  }

  fun filterToday(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Today"
    AppLanguage.AR -> "اليوم"
  }

  fun filterUpcoming(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Upcoming"
    AppLanguage.AR -> "القادمة"
  }

  fun filterCompleted(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Completed"
    AppLanguage.AR -> "المكتملة"
  }

  fun filterOverdue(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Overdue"
    AppLanguage.AR -> "المتأخرة"
  }

  fun priorityLabel(priority: Priority, lang: AppLanguage) = when (priority) {
    Priority.HIGH -> if (lang == AppLanguage.EN) "High" else "عالية"
    Priority.MEDIUM -> if (lang == AppLanguage.EN) "Medium" else "متوسطة"
    Priority.LOW -> if (lang == AppLanguage.EN) "Low" else "منخفضة"
  }

  fun statusLabel(status: ProjectStatus, lang: AppLanguage) = when (status) {
    ProjectStatus.ON_TRACK -> if (lang == AppLanguage.EN) "On Track" else "على المسار"
    ProjectStatus.IN_PROGRESS -> if (lang == AppLanguage.EN) "In Progress" else "قيد التنفيذ"
    ProjectStatus.COMPLETED -> if (lang == AppLanguage.EN) "Completed" else "مكتمل"
    ProjectStatus.PLANNING -> if (lang == AppLanguage.EN) "Planning" else "تخطيط"
  }

  fun emptyTasks(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "No tasks found for this filter"
    AppLanguage.AR -> "لا توجد مهام تطابق هذا التصنيف"
  }

  fun emptyProjects(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "No projects found"
    AppLanguage.AR -> "لا توجد مشاريع مضافة"
  }

  // Dialogs
  fun createTaskTitle(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Create New Task"
    AppLanguage.AR -> "إنشاء مهمة جديدة"
  }

  fun taskTitleHint(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Task title"
    AppLanguage.AR -> "عنوان المهمة"
  }

  fun taskDescHint(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Details (optional)"
    AppLanguage.AR -> "تفاصيل إضافية (اختياري)"
  }

  fun selectPriority(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Priority"
    AppLanguage.AR -> "مستوى الأولوية"
  }

  fun selectWorkspace(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Workspace"
    AppLanguage.AR -> "مساحة العمل"
  }

  fun cancel(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Cancel"
    AppLanguage.AR -> "إلغاء"
  }

  fun create(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Create"
    AppLanguage.AR -> "إنشاء"
  }

  // Projects Screen
  fun createProjectTitle(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Create New Project"
    AppLanguage.AR -> "إنشاء مشروع جديد"
  }

  fun projectNameHint(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Project name"
    AppLanguage.AR -> "اسم المشروع"
  }

  fun projectCategoryHint(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Category (e.g. Design, Strategy)"
    AppLanguage.AR -> "التصنيف (مثال: تصميم، استراتيجية)"
  }

  fun progressSummary(completed: Int, total: Int, lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "$completed of $total tasks done"
    AppLanguage.AR -> "تم إنجاز $completed من أصل $total مهمة"
  }

  fun dueOn(date: String, lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Due $date"
    AppLanguage.AR -> "الموعد $date"
  }

  // Calendar Screen
  fun agendaFor(dayName: String, lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Agenda for $dayName"
    AppLanguage.AR -> "جدول أعمال $dayName"
  }

  fun noAgendaEvents(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "No scheduled events for this day"
    AppLanguage.AR -> "لا توجد فعاليات مجدولة لهذا اليوم"
  }

  // Settings Screen
  fun settings(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Settings"
    AppLanguage.AR -> "الإعدادات"
  }

  fun appearance(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Theme & Appearance"
    AppLanguage.AR -> "المظهر والسمة"
  }

  fun themeLight(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Light"
    AppLanguage.AR -> "فاتح"
  }

  fun themeDark(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Dark"
    AppLanguage.AR -> "داكن"
  }

  fun themeSystem(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "System Default"
    AppLanguage.AR -> "تلقائي النظام"
  }

  fun languageSetting(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Language / اللغة"
    AppLanguage.AR -> "اللغة / Language"
  }

  fun languageEnglish() = "English (US)"
  fun languageArabic() = "العربية (Arabic)"

  fun notifications(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Notifications & Reminders"
    AppLanguage.AR -> "الإشعارات والتذكيرات"
  }

  fun dailyReminderDesc(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Morning daily planning & evening recap"
    AppLanguage.AR -> "تذكير بالتخطيط الصباحي وملخص المساء"
  }

  fun profile(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "User Profile"
    AppLanguage.AR -> "الملف الشخصي"
  }

  fun profileName() = "Alex Morgan"
  fun profileEmail() = "alex.morgan@taskora.io"

  fun about(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "About Taskora"
    AppLanguage.AR -> "حول تطبيق تاسكورا"
  }

  fun aboutDesc(lang: AppLanguage) = when (lang) {
    AppLanguage.EN -> "Version 1.0.0 • Premium Native Android productivity platform designed with precision and clarity."
    AppLanguage.AR -> "الإصدار 1.0.0 • منصة إنتاجية أندرويد أصلية مصممة بأعلى درجات الدقة والوضوح."
  }
}
