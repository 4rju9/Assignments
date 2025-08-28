package app.netlify.dev4rju9.taskmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import app.netlify.dev4rju9.taskmanager.util.Utility.getNotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager(applicationContext)
                .createNotificationChannel(
                    NotificationChannel(
                        "task_channel",
                        "Task Channel",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )
        }

    }
}