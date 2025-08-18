package app.netlify.dev4rju9.geologger

import android.app.Application
import app.netlify.dev4rju9.geologger.data.local.GeofenceDatabase
import app.netlify.dev4rju9.geologger.utils.NotificationHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GeoLoggerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHandler.createNotificationChannel(applicationContext)
    }
}
