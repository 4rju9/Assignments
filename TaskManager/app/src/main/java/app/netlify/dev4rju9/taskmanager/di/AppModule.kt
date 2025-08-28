package app.netlify.dev4rju9.taskmanager.di

import android.content.Context
import androidx.room.Room
import app.netlify.dev4rju9.taskmanager.model.repository.Repository
import app.netlify.dev4rju9.taskmanager.model.repository.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase (
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "task_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideRepository (
        db: TaskDatabase
    ) = Repository(
        dao = db.getDao()
    )

}