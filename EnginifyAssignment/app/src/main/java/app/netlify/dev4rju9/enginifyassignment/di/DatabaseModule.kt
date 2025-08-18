package app.netlify.dev4rju9.enginifyassignment.di

import android.content.Context
import androidx.room.Room
import app.netlify.dev4rju9.enginifyassignment.data.local.PostDao
import app.netlify.dev4rju9.enginifyassignment.data.local.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PostDatabase =
        Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "post_db"
        ).build()

    @Singleton
    @Provides
    fun providePostDao(db: PostDatabase): PostDao = db.postDao()
}