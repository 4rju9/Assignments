package app.netlify.dev4rju9.taskmanager.view.screens.addtaskscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.netlify.dev4rju9.taskmanager.viewmodel.AddTaskViewModel
import java.util.Calendar

@Composable
fun AddTaskScreen (
    modifier: Modifier = Modifier,
    viewModel: AddTaskViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {

    val state = viewModel.state.value
    val context = LocalContext.current
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect (state.message) {
        if (state.message.isNotBlank()) {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect (state.saved) {
        if (state.saved) {
            onSuccess()
        }
    }

    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.title,
            shape = RoundedCornerShape(16.dp),
            onValueChange = { viewModel.updateTitle(it) },
            label = { Text(text = "Title") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.description,
            shape = RoundedCornerShape(16.dp),
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text(text = "Description") }
        )

        if (showTimePicker) {
            DialExample(
                onConfirm = {
                    viewModel.updateTime(it)
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false }
            )
        } else {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                onClick = {
                    showTimePicker = true
                }
            ) {
                Text(text = "Pick Time")
            }
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = state.title.isNotEmpty(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            onClick = {
                viewModel.addTask(context)
            }
        ) {
            Text(text = "Add Task")
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialExample(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val originalTime = Calendar.getInstance()
    originalTime.set(Calendar.SECOND, 0)
    originalTime.set(Calendar.MILLISECOND, 0)
    val currentTime = Calendar.getInstance()
    currentTime.set(Calendar.SECOND, 0)
    currentTime.set(Calendar.MILLISECOND, 0)

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column {
        TimePicker(state = timePickerState)

        Button(onClick = onDismiss) {
            Text("Dismiss picker")
        }

        Button(onClick = {
            val hour = timePickerState.hour - originalTime.get(Calendar.HOUR_OF_DAY)
            val minute = timePickerState.minute - originalTime.get(Calendar.MINUTE)
            if (hour > 0) currentTime.add(Calendar.HOUR_OF_DAY, hour) else {
                Log.d("x4rju9", "hour: $hour")
            }
            if (minute > 0) currentTime.add(Calendar.MINUTE, minute) else {
                Log.d("x4rju9", "minute: $minute")
            }
            onConfirm(currentTime.timeInMillis)
        }) {
            Text("Confirm selection")
        }
    }
}