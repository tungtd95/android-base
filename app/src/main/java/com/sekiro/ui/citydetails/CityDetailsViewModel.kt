package com.sekiro.ui.citydetails

import androidx.lifecycle.MutableLiveData
import com.sekiro.data.models.City
import com.sekiro.data.models.Weather
import com.sekiro.data.repo.WeatherRepo
import com.sekiro.data.utils.ErrorHandler
import com.sekiro.ui.base.BaseViewModel
import com.sekiro.utils.add
import com.sekiro.utils.applyScheduleSingle

class CityDetailsViewModel(
    errorHandler: ErrorHandler,
    private val weatherRepo: WeatherRepo
) : BaseViewModel(errorHandler) {

    val weather = MutableLiveData<Pair<City, Weather>>()

    fun setCity(city: City?) {
        city ?: return
        weatherRepo.getWeatherByCity(city)
            .compose(applyScheduleSingle())
            .subscribe({
                weather.postValue(Pair(city, it))
            }, { handleError(it) })
            .add(this)
    }

    fun setCityId(cityId: Int?) {
        cityId ?: return
        weatherRepo.getWeatherByCityId(cityId)
            .compose(applyScheduleSingle())
            .subscribe({
                weather.postValue(it)
            }, { handleError(it) })
            .add(this)
    }
}