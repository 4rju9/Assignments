package app.netlify.dev4rju9.taskmanager.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.BackoffPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import app.netlify.dev4rju9.taskmanager.model.repository.Repository
import app.netlify.dev4rju9.taskmanager.util.TaskScheduler
import app.netlify.dev4rju9.taskmanager.util.Utility.getCurrentTime
import app.netlify.dev4rju9.taskmanager.view.screens.addtaskscreen.AddTaskState
import app.netlify.dev4rju9.taskmanager.view.screens.addtaskscreen.toTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: Repository,
    private val bundle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddTaskState())
    val state: State<AddTaskState> = _state

    init {
        viewModelScope.launch {
            bundle.get<Long>("taskId")?.also { taskId ->
                if (taskId != -1L) {
                    try {
                        val task = repository.getTask(taskId)
                        _state.value = _state.value.copy(
                            id = task.id,
                            title = task.title,
                            description = task.description,
                            date = task.date
                        )
                    } catch (e: Exception) { Log.d("x4rju9", "Couldn't find task with $taskId") }
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun updateMessage (error: String) {
        _state.value = _state.value.copy(message = error)
    }

    fun updateSaved (saved: Boolean) {
        _state.value = _state.value.copy(saved = saved)
    }

    fun updateTime (time: Long) {
        _state.value = _state.value.copy(date = time)
    }

    fun addTask(context: Context) {
        viewModelScope.launch {

            if (state.value.title.isBlank()) {
                updateMessage("Title cannot be empty.")
                return@launch
            }

            var task = state.value.toTask()

            val current = getCurrentTime()
            val scheduled = task.date
            val delay = scheduled - current

            Log.d("x4rju9", "Current: $current\nScheduled: $scheduled")
            Log.d("x4rju9", "Delay: $delay")

            repository.addTask(task)
            task = repository.getTask(task.date)

            val inputData = workDataOf(
                "title" to task.title,
                "description" to task.description,
                "task_id" to task.date
            )

            val request = OneTimeWorkRequestBuilder<TaskScheduler>()
                .setInitialDelay(
                    delay,
                    TimeUnit.MILLISECONDS
                )
                .setInputData(inputData)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    10,
                    TimeUnit.SECONDS
                )
                .build()

            WorkManager.getInstance(context).apply {
                enqueueUniqueWork(
                    "task_${task.id}",
                    ExistingWorkPolicy.REPLACE,
                    request
                )
            }

            repository.addTask(task.copy(work_id = request.id))

            updateMessage("Task added successfully")
            updateSaved(true)

        }
    }

}