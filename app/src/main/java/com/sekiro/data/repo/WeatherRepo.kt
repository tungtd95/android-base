package com.sekiro.data.repo

import com.sekiro.data.local.WeatherDatabase
import com.sekiro.data.models.City
import com.sekiro.data.models.Weather
import com.sekiro.data.service.WeatherService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class WeatherRepo(
    private val service: WeatherService,
    private val localDatabase: WeatherDatabase
) {

    fun getWeatherByCity(city: City): Single<Weather> =
        service.getWeatherByCityLatLng(city.lat, city.lon)

    fun getWeatherByCityId(cityId: Int): Single<Pair<City, Weather>> =
        localDatabase.weatherDao().getCityById(cityId)
            .flatMapSingle { city ->
                service.getWeatherByCityLatLng(city.lat, city.lon)
                    .map {
                        Pair(city, it)
                    }
            }

    fun searchCitiesByName(query: String): Single<List<City>> =
        service.getCitiesByName(query)

    fun getCitiesFlow(): Flowable<List<City>> = localDatabase.weatherDao().getCitiesFlow()

    fun addCity(city: City): Completable {
        return localDatabase.weatherDao()
            .getCitiesOneShot()
            .map {
                it.any { c -> c.getFullName() == city.getFullName() }
            }
            .flatMapCompletable {
                if (!it) {
                    localDatabase.weatherDao().add(city)
                } else {
                    Completable.complete()
                }
            }
    }

    fun deleteCity(city: City): Completable = localDatabase.weatherDao().remove(city)
}