package app.netlify.dev4rju9.geologger.data.repository

import app.netlify.dev4rju9.geologger.data.local.GeofenceDatabase
import app.netlify.dev4rju9.geologger.data.local.dao.GeofenceDao
import app.netlify.dev4rju9.geologger.data.local.dao.GeofenceVisitDao
import app.netlify.dev4rju9.geologger.data.local.entity.EntryEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceVisit
import javax.inject.Inject

class GeofenceRepository @Inject constructor (db: GeofenceDatabase) {

    private val geofenceVisitDao: GeofenceVisitDao = db.geofenceVisitDao()
    private val geofenceDao: GeofenceDao = db.geofenceDao()

    suspend fun insertGeofenceVisit(geofenceVisit: GeofenceVisit) {
        geofenceVisitDao.insertGeofenceVisit(geofenceVisit)
    }

    suspend fun getAllGeofenceVisits(): List<GeofenceVisit> {
        return geofenceVisitDao.getAllGeofenceVisits()
    }

    suspend fun insertEntryTime (entryEntity: EntryEntity) {
        geofenceVisitDao.insertEntryTime(entryEntity)
    }

    suspend fun getEntryTime (id: Int): EntryEntity {
        return geofenceVisitDao.getEntryTime(id)
    }

    suspend fun insertGeofence(geofence: GeofenceEntity) = geofenceDao.insertGeofence(geofence)

    suspend fun getAllGeofences() = geofenceDao.getAllGeofences()

    suspend fun deleteGeofence(id: String) = geofenceDao.deleteGeofence(id)

}
