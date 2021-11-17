package com.moddroid

import android.app.Application
import com.moddroid.di.appModule
import com.moddroid.di.dataModule
import com.moddroid.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, dataModule, viewModelModule))
        }
    }
}
