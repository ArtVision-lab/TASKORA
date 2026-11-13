package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.EventBusy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.CalendarItem
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DeepForestGreen
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.VibrantOrange

@Composable
fun CalendarScreen(
  calendarItems: List<CalendarItem>,
  selectedDayIndex: Int,
  onDaySelected: (Int) -> Unit,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val isAr = language == AppLanguage.AR
  val daysEn = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
  val daysAr = listOf("إثنين", "ثلاثاء", "أربعاء", "خميس", "جمعة", "سبت", "أحد")
  val dates = listOf("09", "10", "11", "12", "13", "14", "15")

  val dayLabels = if (isAr) daysAr else daysEn
  val selectedDayName = dayLabels.getOrElse(selectedDayIndex) { "" }

  val filteredAgenda = calendarItems.filter { it.dayIndex == selectedDayIndex }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = TaskoraStrings.navCalendar(language),
        style = MaterialTheme.typography.headlineLarge.copy(
          fontWeight = FontWeight.ExtraBold
        ),
        color = MaterialTheme.colorScheme.onBackground
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = if (isAr) "نوفمبر ٢٠٢٦ • جدول الأسبوع" else "November 2026 • Weekly Timeline",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    // Interactive 7-Day Strip
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        dayLabels.indices.forEach { index ->
          val isSelected = index == selectedDayIndex
          val isToday = index == 2 // Wednesday 11

          val bgColor by animateColorAsState(
            targetValue = if (isSelected) DeepForestGreen else MaterialTheme.colorScheme.surface,
            label = "dayBg"
          )
          val textColor by animateColorAsState(
            targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            label = "dayText"
          )

          Box(
            modifier = Modifier
              .weight(1f)
              .padding(horizontal = 2.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(bgColor)
              .border(
                width = if (isToday && !isSelected) 1.5.dp else 1.dp,
                color = if (isToday && !isSelected) EmeraldGreen else if (isSelected) DeepForestGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(14.dp)
              )
              .clickable { onDaySelected(index) }
              .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = dayLabels[index],
                fontSize = 10.sp,
                color = if (isSelected) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = dates[index],
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
              )
              if (isToday) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                  modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color.White else EmeraldGreen)
                )
              }
            }
          }
        }
      }
    }

    // Selected Day Agenda Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = TaskoraStrings.agendaFor(selectedDayName, language),
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
          ),
          color = MaterialTheme.colorScheme.onSurface
        )

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(EmeraldGreen.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "${filteredAgenda.size} ${if (isAr) "فعاليات" else "events"}",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = EmeraldGreen
          )
        }
      }
    }

    // Agenda List
    if (filteredAgenda.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
              width = 1.dp,
              color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
              shape = RoundedCornerShape(16.dp)
            )
            .padding(32.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = Icons.Outlined.EventBusy,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
              modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = TaskoraStrings.noAgendaEvents(language),
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    } else {
      items(filteredAgenda, key = { it.id }) { item ->
        val title = if (isAr) item.titleAr else item.title
        val duration = if (isAr) item.durationAr else item.duration
        val category = if (isAr) item.categoryAr else item.category

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
            .padding(14.dp)
            .testTag("agenda_item_${item.id}")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Time Pillar
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.width(68.dp)
            ) {
              Text(
                text = item.time,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = EmeraldGreen
              )
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.AccessTime,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                  text = duration,
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Box(
              modifier = Modifier
                .height(36.dp)
                .width(2.dp)
                .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.SemiBold,
                  textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                ),
                color = if (item.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
              )
              Spacer(modifier = Modifier.height(4.dp))
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(MaterialTheme.colorScheme.surfaceVariant)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = category,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Medium,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            if (item.isCompleted) {
              Box(
                modifier = Modifier
                  .size(22.dp)
                  .clip(CircleShape)
                  .background(SuccessGreen.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Completed",
                  tint = SuccessGreen,
                  modifier = Modifier.size(14.dp)
                )
              }
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
