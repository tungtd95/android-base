package com.sekiro.data.service

import com.sekiro.BuildConfig
import com.sekiro.data.models.City
import com.sekiro.data.models.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather?appid=${BuildConfig.OPEN_WEATHER_KEY}")
    fun getWeatherByCityName(
        @Query("q") cityName: String
    ): Single<Weather>

    @GET("/geo/1.0/direct?appid=${BuildConfig.OPEN_WEATHER_KEY}")
    fun getCitiesByName(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5,
    ): Single<List<City>>
}