package app.netlify.dev4rju9.vijayiassignment.di

import app.netlify.dev4rju9.vijayiassignment.model.remote.retrofit.FoodApi
import app.netlify.dev4rju9.vijayiassignment.model.repository.MainRepository
import app.netlify.dev4rju9.vijayiassignment.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<FoodApi> {
        Retrofit.Builder()
            .baseUrl("https://livetv.4rju9.workers.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }
    single<MainRepository> {
        MainRepository(get())
    }
    viewModel {
        MainViewModel(get())
    }
}