package app.netlify.dev4rju9.clickretinaassignment.di

import app.netlify.dev4rju9.clickretinaassignment.data.remote.ProfileApi
import app.netlify.dev4rju9.clickretinaassignment.data.remote.ProfileApi.Companion.BASE_URL
import app.netlify.dev4rju9.clickretinaassignment.data.repository.UserProfileRepositoryImpl
import app.netlify.dev4rju9.clickretinaassignment.domain.repository.UserProfileRepository
import app.netlify.dev4rju9.clickretinaassignment.domain.user_case.GetUserProfile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserProfileModule {

    @Provides
    @Singleton
    fun provideGetUserProfileUserCase (repository: UserProfileRepository) = GetUserProfile(repository)

    @Provides
    @Singleton
    fun provideUserProfileRepository (api: ProfileApi) : UserProfileRepository = UserProfileRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideProfileApi () = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProfileApi::class.java)

}