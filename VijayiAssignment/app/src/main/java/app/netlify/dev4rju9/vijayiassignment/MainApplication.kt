package app.netlify.dev4rju9.vijayiassignment

import android.app.Application
import app.netlify.dev4rju9.vijayiassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }

    }
}