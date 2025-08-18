package app.netlify.dev4rju9.geologger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geofence_table")
data class GeofenceEntity(
    @PrimaryKey val id: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Float
)