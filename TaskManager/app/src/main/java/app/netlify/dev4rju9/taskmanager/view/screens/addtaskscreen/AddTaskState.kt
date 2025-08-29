package app.netlify.dev4rju9.taskmanager.view.screens.addtaskscreen

import app.netlify.dev4rju9.taskmanager.model.Task
import app.netlify.dev4rju9.taskmanager.util.Utility.getCurrentTime

data class AddTaskState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val message: String = "",
    val saved: Boolean = false,
    val date: Long = getCurrentTime()
)

fun AddTaskState.toTask () : Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = false,
        date = date
    )
}
