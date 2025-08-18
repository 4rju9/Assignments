package app.netlify.dev4rju9.geologger.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.netlify.dev4rju9.geologger.data.local.dao.GeofenceDao
import app.netlify.dev4rju9.geologger.data.local.dao.GeofenceVisitDao
import app.netlify.dev4rju9.geologger.data.local.entity.EntryEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceEntity
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceVisit

@Database(entities = [GeofenceVisit::class, EntryEntity::class, GeofenceEntity::class], version = 4, exportSchema = false)
abstract class GeofenceDatabase : RoomDatabase() {

    abstract fun geofenceVisitDao(): GeofenceVisitDao

    abstract fun geofenceDao(): GeofenceDao

    companion object {
        @Volatile
        private var INSTANCE: GeofenceDatabase? = null

        fun getDatabase(context: Context): GeofenceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GeofenceDatabase::class.java,
                    "geofence_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
