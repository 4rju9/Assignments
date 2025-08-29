package app.netlify.dev4rju9.taskmanager.view.screens.listscreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.netlify.dev4rju9.taskmanager.model.Task
import app.netlify.dev4rju9.taskmanager.util.Utility.getTime
import app.netlify.dev4rju9.taskmanager.viewmodel.ListViewModel

@Composable
fun ListScreen (
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
    onItemClicked: (Task) -> Unit
) {

    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect (state.message) {
        if (state.message.isNotBlank()) {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    Box (
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn (
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.tasks) { task ->
                TaskItem(
                    task = task,
                    onDelete = {
                        viewModel.deleteTask(context, task)
                    },
                    onItemClicked = onItemClicked
                )
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}

@Composable
fun TaskItem (
    task: Task,
    onDelete: () -> Unit,
    onItemClicked: (Task) -> Unit
) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults
            .cardElevation(8.dp),
        onClick = {
            onItemClicked(task)
        }
    ) {
        Column (
            modifier = Modifier
                .background(if (task.isCompleted) Color.Green.copy(0.5f) else Color.Red.copy(0.5f))
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = getTime(task.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton (
                    onClick = { onDelete() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

        }
    }

}