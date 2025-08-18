package app.netlify.dev4rju9.geologger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geofence_visits")
data class GeofenceVisit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val locationName: String,
    val entryTime: Long,
    val exitTime: Long,
    val duration: Long
)
