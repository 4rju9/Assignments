package app.netlify.dev4rju9.taskmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import app.netlify.dev4rju9.taskmanager.util.Utility.getNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

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