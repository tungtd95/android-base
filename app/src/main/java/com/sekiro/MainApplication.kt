package com.sekiro

import android.app.Application
import app.rive.runtime.kotlin.core.Rive
import com.sekiro.di.appModule
import com.sekiro.di.dataModule
import com.sekiro.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, dataModule, viewModelModule))
        }
        Rive.init(this)
    }
}
