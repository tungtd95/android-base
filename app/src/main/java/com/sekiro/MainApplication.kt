package com.sekiro

import android.app.Application
import app.rive.runtime.kotlin.core.Rive
import com.google.android.gms.ads.MobileAds
import com.sekiro.di.appModule
import com.sekiro.di.dataModule
import com.sekiro.di.viewModelModule
import com.sekiro.utils.AppOpenManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    private var appOpenManager: AppOpenManager? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, dataModule, viewModelModule))
        }
        Rive.init(this)
        MobileAds.initialize(this)
        appOpenManager = AppOpenManager(this).apply { fetchAd() }
    }
}
