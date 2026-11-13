package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.Priority
import com.example.data.model.WorkspaceType
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DeepForestGreen

@Composable
fun CreateTaskDialog(
  onDismiss: () -> Unit,
  onConfirm: (title: String, titleAr: String, desc: String, descAr: String, priority: Priority, workspace: WorkspaceType) -> Unit,
  language: AppLanguage
) {
  var title by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var selectedPriority by remember { mutableStateOf(Priority.HIGH) }
  var selectedWorkspace by remember { mutableStateOf(WorkspaceType.PERSONAL) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = TaskoraStrings.createTaskTitle(language),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text(TaskoraStrings.taskTitleHint(language)) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_task_title"),
          singleLine = true,
          shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text(TaskoraStrings.taskDescHint(language)) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_task_desc"),
          maxLines = 3,
          shape = RoundedCornerShape(12.dp)
        )

        // Priority Selection
        Text(
          text = TaskoraStrings.selectPriority(language),
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Priority.entries.forEach { priority ->
            val isSelected = selectedPriority == priority
            Box(
              modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                  if (isSelected) DeepForestGreen else MaterialTheme.colorScheme.surfaceVariant
                )
                .clickable { selectedPriority = priority }
                .testTag("priority_select_${priority.name.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = TaskoraStrings.priorityLabel(priority, language),
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }

        // Workspace Selection
        Text(
          text = TaskoraStrings.selectWorkspace(language),
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf(WorkspaceType.PERSONAL, WorkspaceType.PROFESSIONAL, WorkspaceType.PROJECTS).forEach { ws ->
            val isSelected = selectedWorkspace == ws
            Box(
              modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                  if (isSelected) DeepForestGreen else MaterialTheme.colorScheme.surfaceVariant
                )
                .clickable { selectedWorkspace = ws }
                .testTag("workspace_select_${ws.name.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = TaskoraStrings.workspace(ws, language),
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (title.isNotBlank()) {
            onConfirm(title, title, description, description, selectedPriority, selectedWorkspace)
          }
        },
        enabled = title.isNotBlank(),
        colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
        modifier = Modifier.testTag("btn_confirm_add_task")
      ) {
        Text(TaskoraStrings.create(language))
      }
    },
    dismissButton = {
      OutlinedButton(
        onClick = onDismiss,
        modifier = Modifier.testTag("btn_cancel_add_task")
      ) {
        Text(TaskoraStrings.cancel(language))
      }
    },
    shape = RoundedCornerShape(20.dp),
    containerColor = MaterialTheme.colorScheme.surface
  )
}
