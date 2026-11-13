package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AppThemeMode
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.BrightEmeraldGreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VibrantOrange

@Composable
fun SettingsScreen(
  currentTheme: AppThemeMode,
  onThemeChanged: (AppThemeMode) -> Unit,
  currentLanguage: AppLanguage,
  onLanguageChanged: (AppLanguage) -> Unit,
  notificationsEnabled: Boolean,
  onNotificationsToggled: (Boolean) -> Unit,
  onBackClick: () -> Unit,
  onReplaySplash: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val isAr = currentLanguage == AppLanguage.AR

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.testTag("btn_settings_back")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onSurface
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = TaskoraStrings.settings(currentLanguage),
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.ExtraBold
          ),
          color = MaterialTheme.colorScheme.onBackground
        )
      }
    }

    // Profile Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(MaterialTheme.colorScheme.surface)
          .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
            shape = RoundedCornerShape(16.dp)
          )
          .padding(16.dp)
          .testTag("card_user_profile")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(52.dp)
              .clip(CircleShape)
              .background(DeepForestGreen),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "AM",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp
            )
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column {
            Text(
              text = TaskoraStrings.profileName(),
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = TaskoraStrings.profileEmail(),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Taskora Pro Member",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = VibrantOrange
            )
          }
        }
      }
    }

    // Language Selector Card
    item {
      SettingsSectionCard(
        title = TaskoraStrings.languageSetting(currentLanguage),
        icon = Icons.Default.Language
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          // English Option
          LanguageOptionRow(
            label = "English (US)",
            subLabel = "Default left-to-right typography",
            isSelected = currentLanguage == AppLanguage.EN,
            onClick = { onLanguageChanged(AppLanguage.EN) },
            testTag = "lang_option_en"
          )
          // Arabic Option
          LanguageOptionRow(
            label = "العربية (Arabic)",
            subLabel = "تخطيط كامل من اليمين إلى اليسار مع خط كايرو",
            isSelected = currentLanguage == AppLanguage.AR,
            onClick = { onLanguageChanged(AppLanguage.AR) },
            testTag = "lang_option_ar"
          )
        }
      }
    }

    // Theme Mode Card
    item {
      SettingsSectionCard(
        title = TaskoraStrings.appearance(currentLanguage),
        icon = Icons.Default.BrightnessAuto
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          ThemeOptionPill(
            label = TaskoraStrings.themeLight(currentLanguage),
            icon = Icons.Default.LightMode,
            isSelected = currentTheme == AppThemeMode.LIGHT,
            onClick = { onThemeChanged(AppThemeMode.LIGHT) },
            modifier = Modifier.weight(1f),
            testTag = "theme_option_light"
          )
          ThemeOptionPill(
            label = TaskoraStrings.themeDark(currentLanguage),
            icon = Icons.Default.DarkMode,
            isSelected = currentTheme == AppThemeMode.DARK,
            onClick = { onThemeChanged(AppThemeMode.DARK) },
            modifier = Modifier.weight(1f),
            testTag = "theme_option_dark"
          )
          ThemeOptionPill(
            label = TaskoraStrings.themeSystem(currentLanguage),
            icon = Icons.Default.BrightnessAuto,
            isSelected = currentTheme == AppThemeMode.SYSTEM,
            onClick = { onThemeChanged(AppThemeMode.SYSTEM) },
            modifier = Modifier.weight(1f),
            testTag = "theme_option_system"
          )
        }
      }
    }

    // Workspace Management Card
    item {
      SettingsSectionCard(
        title = if (isAr) "إدارة مساحات العمل" else "Workspace Management",
        icon = Icons.Default.Workspaces
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          WorkspaceSummaryRow(
            name = if (isAr) "شخصي" else "Personal",
            info = if (isAr) "٢ مهام • ١ مشروع" else "2 tasks • 1 project"
          )
          WorkspaceSummaryRow(
            name = if (isAr) "مهني" else "Professional",
            info = if (isAr) "٣ مهام • ٢ مشاريع" else "3 tasks • 2 projects"
          )
          WorkspaceSummaryRow(
            name = if (isAr) "مشاريع" else "Projects",
            info = if (isAr) "٢ مهام • ١ مشروع" else "2 tasks • 1 project"
          )
        }
      }
    }

    // Notifications Card
    item {
      SettingsSectionCard(
        title = TaskoraStrings.notifications(currentLanguage),
        icon = Icons.Default.Notifications
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (isAr) "التنبيهات اليومية" else "Daily Productivity Reminders",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = TaskoraStrings.dailyReminderDesc(currentLanguage),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          Switch(
            checked = notificationsEnabled,
            onCheckedChange = onNotificationsToggled,
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = EmeraldGreen
            ),
            modifier = Modifier.testTag("switch_notifications")
          )
        }
      }
    }

    // About Card
    item {
      SettingsSectionCard(
        title = TaskoraStrings.about(currentLanguage),
        icon = Icons.Outlined.Info
      ) {
        Text(
          text = TaskoraStrings.aboutDesc(currentLanguage),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        val isDark = MaterialTheme.colorScheme.background == DarkBackground
        val actionAccent = if (isDark) BrightEmeraldGreen else DeepForestGreen
        androidx.compose.material3.OutlinedButton(
          onClick = onReplaySplash,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("btn_replay_splash")
        ) {
          Icon(
            imageVector = Icons.Default.BrightnessAuto,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = actionAccent
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (isAr) "عرض شاشة الترحيب (Splash)" else "Preview Splash Screen",
            fontSize = 12.sp,
            color = actionAccent,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun SettingsSectionCard(
  title: String,
  icon: ImageVector,
  content: @Composable () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colorScheme.surface)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
        shape = RoundedCornerShape(16.dp)
      )
      .padding(16.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        val isDark = MaterialTheme.colorScheme.background == DarkBackground
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (isDark) BrightEmeraldGreen else DeepForestGreen,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
      }
      Spacer(modifier = Modifier.height(12.dp))
      content()
    }
  }
}

@Composable
fun LanguageOptionRow(
  label: String,
  subLabel: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  testTag: String
) {
  val isDark = MaterialTheme.colorScheme.background == DarkBackground
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
      .border(
        width = 1.5.dp,
        color = if (isSelected) (if (isDark) BrightEmeraldGreen else EmeraldGreen) else Color.Transparent,
        shape = RoundedCornerShape(12.dp)
      )
      .clickable { onClick() }
      .padding(12.dp)
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = label,
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
          color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = subLabel,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      if (isSelected) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(EmeraldGreen)
        )
      }
    }
  }
}

@Composable
fun ThemeOptionPill(
  label: String,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  testTag: String
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(if (isSelected) DeepForestGreen else MaterialTheme.colorScheme.surfaceVariant)
      .clickable { onClick() }
      .padding(vertical = 12.dp, horizontal = 6.dp)
      .testTag(testTag),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(18.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = label,
        fontSize = 10.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
        maxLines = 1
      )
    }
  }
}

@Composable
fun WorkspaceSummaryRow(
  name: String,
  info: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
      .padding(horizontal = 12.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = name,
      style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
      color = MaterialTheme.colorScheme.onSurface
    )
    Text(
      text = info,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}
