package app.netlify.dev4rju9.taskmanager.view.screens.listscreen

import app.netlify.dev4rju9.taskmanager.model.Task
import app.netlify.dev4rju9.taskmanager.util.Utility.getCurrentTime

data class ListState (
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = ""
)
