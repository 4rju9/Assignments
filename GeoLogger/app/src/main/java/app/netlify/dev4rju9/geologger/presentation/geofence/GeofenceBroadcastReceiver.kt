package app.netlify.dev4rju9.geologger.presentation.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import app.netlify.dev4rju9.geologger.data.local.GeofenceDatabase
import app.netlify.dev4rju9.geologger.data.local.entity.EntryEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceVisit
import app.netlify.dev4rju9.geologger.data.repository.GeofenceRepository
import app.netlify.dev4rju9.geologger.utils.NotificationHandler
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) {
            Log.d("Arjun", "onReceive: Context or Intent is null")
            return
        }

        Log.d("Arjun", "onReceive: Geofence Broadcast Receiver Triggered")

        val event = GeofencingEvent.fromIntent(intent)
        if (event?.hasError() == true) {
            Log.e("Arjun", "Geofencing Event Error: ${event.errorCode}")
            return
        }

        val database = GeofenceDatabase.getDatabase(context)
        val repository = GeofenceRepository(database)

        val transitionType = event!!.geofenceTransition
        val geofenceId = event.triggeringGeofences?.firstOrNull()?.requestId ?: "Unknown"

        CoroutineScope(Dispatchers.IO).launch {
            when (transitionType) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> {
                    val msEntry = System.currentTimeMillis()
                    repository.insertEntryTime(EntryEntity(id = 1, entry_time = msEntry))
                    Log.d("Arjun", "onReceive: Geofence entered: $geofenceId")

                    NotificationHandler.sendNotification(
                        context.applicationContext,
                        "Geofence Entered",
                        "You have entered $geofenceId\nand your visit will be saved once leave the area."
                    )
                }

                Geofence.GEOFENCE_TRANSITION_EXIT -> {
                    val entry = repository.getEntryTime(1)
                    val msExit = System.currentTimeMillis()
                    val duration = msExit - entry.entry_time
                    val geofenceVisit = GeofenceVisit(
                        locationName = geofenceId,
                        entryTime = entry.entry_time,
                        exitTime = msExit,
                        duration = duration
                    )
                    repository.insertGeofenceVisit(geofenceVisit)
                    Log.d("Arjun", "onReceive: Geofence exit logged for $geofenceId")

                    NotificationHandler.sendNotification(
                        context.applicationContext,
                        "Geofence Exit",
                        "You have exited $geofenceId\nand your visit is saved."
                    )

                }
                else -> Log.d("Arjun", "onReceive: Unknown transition type: $transitionType")
            }
        }
    }
}
