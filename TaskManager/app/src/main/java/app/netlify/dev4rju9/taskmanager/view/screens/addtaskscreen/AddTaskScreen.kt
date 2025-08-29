package app.netlify.dev4rju9.taskmanager.view.screens.addtaskscreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
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
    val scrollState = rememberScrollState()

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

    LaunchedEffect (showTimePicker) {
        if (showTimePicker) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
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
            label = { Text(text = "Title") },
            maxLines = 1
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.description,
            shape = RoundedCornerShape(16.dp),
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text(text = "Description") },
            minLines = 3
        )

        if (showTimePicker) {
            DateTimePicker(
                onConfirm = {
                    viewModel.updateTime(it)
                    showTimePicker = false
                    if (state.title.isNotEmpty()) {
                        viewModel.addTask(context)
                    }
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

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = remember { Calendar.getInstance() }
    val originalHour = calendar.get(Calendar.HOUR_OF_DAY)
    val originalMinute = calendar.get(Calendar.MINUTE)

    var showDatePicker by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = calendar.timeInMillis
        )
        DatePicker(state = datePickerState)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Button(onClick = onDismiss) { Text("Cancel") }
            Button(onClick = {
                val selectedDate = datePickerState.selectedDateMillis ?: calendar.timeInMillis
                calendar.timeInMillis = selectedDate
                calendar.set(Calendar.HOUR_OF_DAY, originalHour)
                calendar.set(Calendar.MINUTE, originalMinute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                showDatePicker = false
                showTimePicker = true
            }) { Text("Next") }
        }
    }

    if (showTimePicker) {

        val timePickerState = rememberTimePickerState(
            initialHour = originalHour % 12,
            initialMinute = originalMinute,
            is24Hour = false
        )

        TimePicker(state = timePickerState)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Button(onClick = { showTimePicker = false; showDatePicker = true }) { Text("Back") }
            Button(onClick = {
                calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                calendar.set(Calendar.MINUTE, timePickerState.minute)
                onConfirm(calendar.timeInMillis)
            }) { Text("Confirm") }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

}