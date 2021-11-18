package com.sekiro.data.repo

import com.sekiro.data.models.City
import com.sekiro.data.models.Weather
import com.sekiro.data.service.WeatherService
import io.reactivex.Single

class WeatherRepo(private val service: WeatherService) {

    fun getWeatherByCityId(cityName: String): Single<Weather> =
        service.getWeatherByCityName(cityName)

    fun searchCitiesByName(query: String): Single<List<City>> =
        service.getCitiesByName(query)
}