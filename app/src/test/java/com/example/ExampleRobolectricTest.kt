package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.AppLanguage
import com.example.data.model.AppThemeMode
import com.example.data.model.Priority
import com.example.data.model.WorkspaceType
import com.example.data.repository.TaskoraRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context matches app name`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Taskora", appName)
  }

  @Test
  fun `task completion toggling and dynamic calculation`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = TaskoraRepository(context)

    val initialTasks = repo.tasks.value
    val target = initialTasks.first { !it.isCompleted }

    repo.toggleTaskCompletion(target.id)
    val updated = repo.tasks.value.first { it.id == target.id }
    assertTrue(updated.isCompleted)

    repo.toggleTaskCompletion(target.id)
    val reverted = repo.tasks.value.first { it.id == target.id }
    assertFalse(reverted.isCompleted)
  }

  @Test
  fun `adding task updates task list`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = TaskoraRepository(context)

    val countBefore = repo.tasks.value.size
    repo.addTask(
      title = "New test task",
      titleAr = "مهمة اختبار جديدة",
      description = "Test desc",
      descriptionAr = "وصف",
      priority = Priority.HIGH,
      workspace = WorkspaceType.PERSONAL
    )

    assertEquals(countBefore + 1, repo.tasks.value.size)
    assertEquals("New test task", repo.tasks.value.first().title)
  }

  @Test
  fun `language and theme persistence`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = TaskoraRepository(context)

    repo.setLanguage(AppLanguage.AR)
    assertEquals(AppLanguage.AR, repo.language.value)

    repo.setThemeMode(AppThemeMode.DARK)
    assertEquals(AppThemeMode.DARK, repo.themeMode.value)
  }

  @Test
  fun `tagline localization matches requirements`() {
    val enTagline = com.example.ui.localization.TaskoraStrings.tagline(AppLanguage.EN)
    val arTagline = com.example.ui.localization.TaskoraStrings.tagline(AppLanguage.AR)

    assertEquals("Organize Your Life. Achieve More.", enTagline)
    assertEquals("نظّم حياتك... وأنجز المزيد.", arTagline)
  }

  @Test
  fun `initial navigation destination is SPLASH and transitions to HOME`() {
    val application = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.TaskoraViewModel(application)

    // Verify initial destination is SPLASH
    assertEquals(com.example.ui.ScreenDestination.SPLASH, viewModel.currentScreen.value)

    // Complete splash
    viewModel.completeSplash()
    assertEquals(com.example.ui.ScreenDestination.HOME, viewModel.currentScreen.value)

    // Replay splash
    viewModel.replaySplash()
    assertEquals(com.example.ui.ScreenDestination.SPLASH, viewModel.currentScreen.value)
  }
}
