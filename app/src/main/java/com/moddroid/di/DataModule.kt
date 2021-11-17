package com.moddroid.di

import com.moddroid.data.repo.ModAppRepo
import com.moddroid.data.service.ModAppService
import com.moddroid.data.utils.ErrorHandler
import com.moddroid.data.utils.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { createModAppService() }
    single { ModAppRepo(get()) }
    single { createErrorHandler() }
}

fun createRetrofit(baseUrl: String): Retrofit {
    val headerInterceptor = HeaderInterceptor()
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun createModAppService(): ModAppService =
    createRetrofit("").create(ModAppService::class.java)

fun createErrorHandler(): ErrorHandler = ErrorHandler()

