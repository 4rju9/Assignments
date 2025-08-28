package app.netlify.dev4rju9.taskmanager.util

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.netlify.dev4rju9.taskmanager.model.repository.local.TaskDatabase
import app.netlify.dev4rju9.taskmanager.util.Utility.createNotification
import app.netlify.dev4rju9.taskmanager.util.Utility.getNotificationManager
import kotlin.random.Random


class TaskScheduler(
    val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker (context, workerParams) {

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
            val dao = Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                "task_database"
            )
                .fallbackToDestructiveMigration()
                .build().getDao()
            val task = dao.getTask(task_id).copy(isCompleted = true)
            dao.addTask(task)
            return Result.success()
        } catch (e: Exception) {
            Log.d("x4rju9", "doWork: ${e.message}")
            return Result.failure()
        }
    }

}