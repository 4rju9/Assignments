package app.netlify.dev4rju9.geologger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.netlify.dev4rju9.geologger.data.local.entity.EntryEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceVisit

@Dao
interface GeofenceVisitDao {

    @Insert
    suspend fun insertGeofenceVisit (geofenceVisit: GeofenceVisit)

    @Query("SELECT * FROM geofence_visits ORDER BY entryTime DESC")
    suspend fun getAllGeofenceVisits (): List<GeofenceVisit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntryTime (entryEntity: EntryEntity)

    @Query("SELECT * FROM entry_time_table WHERE id = :id")
    suspend fun getEntryTime (id: Int): EntryEntity

}
