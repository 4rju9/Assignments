package app.netlify.dev4rju9.geologger.di

import android.app.Application
import android.content.Context
import app.netlify.dev4rju9.geologger.data.repository.GeofenceRepository
import app.netlify.dev4rju9.geologger.presentation.geofence.GeofenceViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScoped
    fun provideGeofenceViewModel(
        repository: GeofenceRepository,
        @ApplicationContext context: Context
    ) = GeofenceViewModel(context as Application, repository)
}