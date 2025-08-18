package app.netlify.dev4rju9.trigrexamassignment.di

import app.netlify.dev4rju9.trigrexamassignment.models.remote.retrofit.FoodishApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://foodish-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideFoodishApi(retrofit: Retrofit): FoodishApiService =
        retrofit.create(FoodishApiService::class.java)
}