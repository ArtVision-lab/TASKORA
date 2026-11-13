package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.AppLanguage
import com.example.data.model.ProjectItem
import com.example.data.model.WorkspaceType
import com.example.ui.components.ProjectCard
import com.example.ui.components.WorkspaceSelectorTabs
import com.example.ui.localization.TaskoraStrings
import com.example.ui.theme.DeepForestGreen

@Composable
fun ProjectsScreen(
  projects: List<ProjectItem>,
  activeWorkspace: WorkspaceType,
  onWorkspaceSelected: (WorkspaceType) -> Unit,
  onNewProjectClick: () -> Unit,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = Color.Transparent,
    floatingActionButton = {
      FloatingActionButton(
        onClick = onNewProjectClick,
        containerColor = DeepForestGreen,
        contentColor = Color.White,
        shape = CircleShape,
        modifier = Modifier.testTag("fab_add_project")
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = TaskoraStrings.newProject(language),
          modifier = Modifier.size(24.dp)
        )
      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      item {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = TaskoraStrings.navProjects(language),
              style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold
              ),
              color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "${projects.size} ${if (language == AppLanguage.AR) "مشاريع نشطة" else "active projects"}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      item {
        WorkspaceSelectorTabs(
          selectedWorkspace = activeWorkspace,
          onWorkspaceSelected = onWorkspaceSelected,
          language = language
        )
      }

      items(projects, key = { it.id }) { project ->
        ProjectCard(
          project = project,
          language = language,
          compact = false
        )
      }

      item {
        Spacer(modifier = Modifier.height(72.dp))
      }
    }
  }
}
