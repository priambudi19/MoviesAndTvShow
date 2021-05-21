package com.priambudi19.moviestvshows

import android.app.Application
import com.priambudi19.moviestvshows.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesTvShowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesTvShowApp)
            androidLogger()
            modules(appModule)
        }
    }
}