package com.sekiro.ui.addcity

import androidx.lifecycle.MutableLiveData
import com.sekiro.data.models.City
import com.sekiro.data.repo.WeatherRepo
import com.sekiro.data.utils.ErrorHandler
import com.sekiro.ui.base.BaseViewModel
import com.sekiro.utils.SingleLiveData
import com.sekiro.utils.add
import com.sekiro.utils.applyScheduleCompletable
import com.sekiro.utils.applyScheduleSingle
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AddCityViewModel(
    errorHandler: ErrorHandler,
    private val weatherRepo: WeatherRepo
) : BaseViewModel(errorHandler) {

    private val publishSubject = PublishSubject.create<String>()

    val cities: MutableLiveData<List<City>> = MutableLiveData()
    val addCityToFavComplete: SingleLiveData<Unit> = SingleLiveData()

    init {
        publishSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .subscribe {
                searchCityByName(it)
            }
            .add(this)
    }

    fun searchCity(query: String) {
        if (query.isBlank()) cities.value = emptyList()
        publishSubject.onNext(query)
    }

    fun addCityToFav(city: City) {
        weatherRepo.addCity(city)
            .compose(applyScheduleCompletable())
            .subscribe({
                addCityToFavComplete.value = Unit
            }, {})
            .add(this)
    }

    private fun searchCityByName(query: String) {
        weatherRepo.searchCitiesByName(query)
            .compose(applyScheduleSingle(loading))
            .subscribe({
                cities.postValue(it.filter { city -> city.lat != null && city.lon != null })
            }, {
                handleError(it)
            })
            .add(this)
    }

}