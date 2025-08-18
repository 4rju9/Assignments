package app.netlify.dev4rju9.geologger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceEntity

@Dao
interface GeofenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeofence(geofence: GeofenceEntity)

    @Query("SELECT * FROM geofence_table")
    suspend fun getAllGeofences(): List<GeofenceEntity>

    @Query("DELETE FROM geofence_table WHERE id = :geofenceId")
    suspend fun deleteGeofence(geofenceId: String)
}