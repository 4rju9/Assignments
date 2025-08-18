package app.netlify.dev4rju9.geologger.di

import android.app.Application
import android.content.Context
import app.netlify.dev4rju9.geologger.data.local.GeofenceDatabase
import app.netlify.dev4rju9.geologger.presentation.geofence.GeofenceHelper
import app.netlify.dev4rju9.geologger.presentation.geofence.GeofenceViewModel
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideDatabase (
        @ApplicationContext context: Context
    ) = GeofenceDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideGeofenceHelper (
        @ApplicationContext context: Context
    ) = GeofenceHelper(context)

    @Singleton
    @Provides
    fun provideGeofencingClient (
        @ApplicationContext context: Context
    ) = LocationServices.getGeofencingClient(context)

}