package app.netlify.dev4rju9.taskmanager.util

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.netlify.dev4rju9.taskmanager.model.repository.Repository
import app.netlify.dev4rju9.taskmanager.util.Utility.createNotification
import app.netlify.dev4rju9.taskmanager.util.Utility.getNotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

@HiltWorker
class TaskScheduler @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val title = inputData.getString("title")
        val description = inputData.getString("description")
        val task_id = inputData.getLong("task_id", 0L)

        Log.d("x4rju9", "doWork: $title, $description")

        val nm = getNotificationManager(context.applicationContext)
        nm.notify(
            Random.nextInt(),
            createNotification(
                context.applicationContext,
                title ?: "To Do",
                description ?: "time to complete your task."
            )
        )

        try {
            val task = repository.getTask(task_id).copy(isCompleted = true)
            repository.addTask(task)
            return Result.success()
        } catch (e: Exception) {
            Log.d("x4rju9", "doWork: ${e.message}")
            return Result.failure()
        }
    }

}