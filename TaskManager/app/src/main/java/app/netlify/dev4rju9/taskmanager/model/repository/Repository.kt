package app.netlify.dev4rju9.taskmanager.model.repository

import app.netlify.dev4rju9.taskmanager.model.Task
import app.netlify.dev4rju9.taskmanager.model.repository.local.TaskDao

class Repository (
    val dao: TaskDao
) {
    fun getTasks () = dao.getTasks()
    suspend fun addTask (task: Task) = dao.addTask(task)
    suspend fun deleteTask (id: Int) = dao.deleteTask(id)
    suspend fun getTask (time: Long) = dao.getTask(time)
}