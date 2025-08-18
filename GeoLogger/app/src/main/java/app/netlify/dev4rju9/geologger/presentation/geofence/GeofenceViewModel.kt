package app.netlify.dev4rju9.geologger.presentation.geofence

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.geologger.data.local.entity.EntryEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceVisit
import app.netlify.dev4rju9.geologger.data.repository.GeofenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeofenceViewModel @Inject constructor (
    application: Application,
    private val geofenceRepository: GeofenceRepository
) : AndroidViewModel(application) {
    val allGeofenceVisits: MutableLiveData<List<GeofenceVisit>> = MutableLiveData()

    fun insertGeofenceVisit(geofenceVisit: GeofenceVisit) {
        viewModelScope.launch {
            geofenceRepository.insertGeofenceVisit(geofenceVisit)
        }
    }

    fun getAllGeofenceVisits() {
        viewModelScope.launch {
            allGeofenceVisits.postValue(geofenceRepository.getAllGeofenceVisits())
        }
    }

    fun insertEntryTime (entryEntity: EntryEntity) {
        viewModelScope.launch {
            geofenceRepository.insertEntryTime(entryEntity)
        }
    }

    suspend fun getEntryTime (id: Int): EntryEntity = geofenceRepository.getEntryTime(id)

    suspend fun insertGeofence(geofence: GeofenceEntity) = geofenceRepository.insertGeofence(geofence)
    suspend fun getAllGeofences() = geofenceRepository.getAllGeofences()
    suspend fun deleteGeofence(id: String) = geofenceRepository.deleteGeofence(id)

}
