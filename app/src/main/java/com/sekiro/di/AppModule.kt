package com.sekiro.di

import android.content.res.Resources
import com.sekiro.MainApplication
import org.koin.dsl.module

val appModule = module {
    single { createResource(get()) }
}

fun createResource(application: MainApplication): Resources = application.resources
