package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.data.model.WorkspaceType
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DeepForestGreen

@Composable
fun CreateProjectDialog(
  onDismiss: () -> Unit,
  onConfirm: (name: String, nameAr: String, category: String, categoryAr: String, workspace: WorkspaceType, dueDate: String) -> Unit,
  language: AppLanguage
) {
  var name by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("") }
  var dueDate by remember { mutableStateOf("Dec 31") }
  var selectedWorkspace by remember { mutableStateOf(WorkspaceType.PROJECTS) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = TaskoraStrings.createProjectTitle(language),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text(TaskoraStrings.projectNameHint(language)) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_project_name"),
          singleLine = true,
          shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
          value = category,
          onValueChange = { category = it },
          label = { Text(TaskoraStrings.projectCategoryHint(language)) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_project_category"),
          singleLine = true,
          shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
          value = dueDate,
          onValueChange = { dueDate = it },
          label = { Text("Due date (e.g. Dec 31)") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_project_due_date"),
          singleLine = true,
          shape = RoundedCornerShape(12.dp)
        )

        Text(
          text = TaskoraStrings.selectWorkspace(language),
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf(WorkspaceType.PROJECTS, WorkspaceType.PROFESSIONAL, WorkspaceType.PERSONAL).forEach { ws ->
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
                .testTag("project_workspace_${ws.name.lowercase()}"),
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
          if (name.isNotBlank()) {
            val cat = if (category.isNotBlank()) category else "General"
            onConfirm(name, name, cat, cat, selectedWorkspace, dueDate)
          }
        },
        enabled = name.isNotBlank(),
        colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
        modifier = Modifier.testTag("btn_confirm_add_project")
      ) {
        Text(TaskoraStrings.create(language))
      }
    },
    dismissButton = {
      OutlinedButton(
        onClick = onDismiss,
        modifier = Modifier.testTag("btn_cancel_add_project")
      ) {
        Text(TaskoraStrings.cancel(language))
      }
    },
    shape = RoundedCornerShape(20.dp),
    containerColor = MaterialTheme.colorScheme.surface
  )
}
