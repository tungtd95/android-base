package com.sekiro.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.sekiro.data.models.City
import com.sekiro.data.models.Weather
import com.sekiro.data.repo.WeatherRepo
import com.sekiro.data.utils.ErrorHandler
import com.sekiro.ui.base.BaseViewModel
import com.sekiro.utils.applyScheduleCompletable
import com.sekiro.utils.applyScheduleFlowable
import com.sekiro.utils.applyScheduleObservable
import io.reactivex.Observable

@SuppressLint("CheckResult")
class HomeViewModel(
    errorHandler: ErrorHandler,
    private val weatherRepo: WeatherRepo
) : BaseViewModel(errorHandler) {

    val weathers: MutableLiveData<List<Pair<City, Weather>>> = MutableLiveData()

    init {
        weatherRepo.getCitiesFlow()
            .compose(applyScheduleFlowable())
            .subscribe({
                getWeathers(it)
            }, {})
    }

    fun removeCity(city: City) {
        val currentData = ArrayList(weathers.value.orEmpty())
        currentData.removeAll { it.first == city }
        weathers.postValue(currentData)
        weatherRepo.deleteCity(city)
            .compose(applyScheduleCompletable())
            .subscribe {}
    }

    private fun getWeathers(cities: List<City>) {
        val requests = cities.map {
            weatherRepo.getWeatherByCity(it).toObservable()
        }
        val weatherExisted = weathers.value.orEmpty().isNotEmpty()
        Observable.zip(requests) { it }
            .compose(
                if (weatherExisted) applyScheduleObservable() else applyScheduleObservable(loading)
            )
            .subscribe({
                val result = arrayListOf<Pair<City, Weather>>()
                it.forEachIndexed { index, any ->
                    if (any is Weather) {
                        result.add(Pair(cities[index], any))
                    }
                }
                weathers.postValue(result)
            }, {})
    }
}