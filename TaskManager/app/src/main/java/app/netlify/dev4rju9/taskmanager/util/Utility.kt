package app.netlify.dev4rju9.taskmanager.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import app.netlify.dev4rju9.taskmanager.R
import app.netlify.dev4rju9.taskmanager.view.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utility {

    fun getCurrentTime () = System.currentTimeMillis()

    fun getNotificationManager (applicationContext: Context) = applicationContext
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotification(
        applicationContext: Context,
        title: String,
        body: String
    ): Notification {

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(applicationContext, "task_channel")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .build()
    }

    fun getTime (time: Long) : String {
        val date = Date(time)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(date)
    }

}