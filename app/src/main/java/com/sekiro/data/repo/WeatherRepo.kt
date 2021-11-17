package com.sekiro.data.repo

import com.sekiro.data.models.Weather
import com.sekiro.data.service.WeatherService
import io.reactivex.Single

class WeatherRepo(private val service: WeatherService) {

    fun getModApps(): Single<Weather> {
        return service.getModApp().map { it.data }
    }
}