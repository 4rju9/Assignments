package app.netlify.dev4rju9.taskmanager.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.WorkManager
import app.netlify.dev4rju9.taskmanager.model.Task
import app.netlify.dev4rju9.taskmanager.model.repository.Repository
import app.netlify.dev4rju9.taskmanager.view.screens.listscreen.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = mutableStateOf(ListState())
    val state: State<ListState> = _state

    init {
        loadTask()
    }

    fun updateMessage (message: String) {
        _state.value = _state.value.copy(message = message)
    }

    fun loadTask () {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            repository.getTasks().collectLatest {
                Log.d("x4rju9", "loadTask: ${it.size}\n$it")
                _state.value = _state.value.copy(tasks = it, isLoading = false)
            }
        }
    }

    fun deleteTask (context: Context, task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task.id)
            task.work_id?.let {
                WorkManager.getInstance(context).cancelWorkById(it)
            }
            updateMessage("Successfully deleted task: ${task.title}.")
        }
    }

}