package app.netlify.dev4rju9.taskmanager.view.screens.listscreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.netlify.dev4rju9.taskmanager.model.Task
import app.netlify.dev4rju9.taskmanager.util.Utility.getTime
import app.netlify.dev4rju9.taskmanager.viewmodel.ListViewModel

@Composable
fun ListScreen (
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.tasks) { task ->
                TaskItem(task) {
                    viewModel.deleteTask(context, task)
                }
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
    onDelete: () -> Unit
) {

    Card (
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineMedium
            )

            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row (
                modifier = Modifier.wrapContentWidth().align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = getTime(task.date),
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = if (task.isCompleted) "Completed" else "Pending",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (task.isCompleted) Color.Green else Color.Red
                )

                TextButton(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    onClick = { onDelete() }
                ) {
                    Text(text = "Delete")
                }
            }

        }
    }

}