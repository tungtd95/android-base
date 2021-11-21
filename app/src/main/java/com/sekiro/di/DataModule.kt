package com.sekiro.di

import android.content.Context
import androidx.room.Room
import com.sekiro.BuildConfig
import com.sekiro.data.local.Pref
import com.sekiro.data.local.WeatherDatabase
import com.sekiro.data.repo.WeatherRepo
import com.sekiro.data.service.WeatherService
import com.sekiro.data.utils.ErrorHandler
import com.sekiro.data.utils.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { createWeatherService() }
    single { createWeatherDatabase(get()) }
    single { createSharedPreference(get()) }
    single { WeatherRepo(get(), get()) }
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

fun createWeatherService(): WeatherService =
    createRetrofit(BuildConfig.BASE_URL).create(WeatherService::class.java)

fun createWeatherDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(context, WeatherDatabase::class.java, "weather-db").build()

fun createErrorHandler(): ErrorHandler = ErrorHandler()

fun createSharedPreference(context: Context): Pref = Pref(context)
