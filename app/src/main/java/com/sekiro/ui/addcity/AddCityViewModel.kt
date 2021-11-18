package com.sekiro.ui.addcity

import androidx.lifecycle.MutableLiveData
import com.sekiro.data.models.City
import com.sekiro.data.repo.WeatherRepo
import com.sekiro.data.utils.ErrorHandler
import com.sekiro.ui.base.BaseViewModel
import com.sekiro.utils.add
import com.sekiro.utils.applyScheduleSingle
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AddCityViewModel(
    errorHandler: ErrorHandler,
    private val weatherRepo: WeatherRepo
) : BaseViewModel(errorHandler) {

    private val publishSubject = PublishSubject.create<String>()

    val cities: MutableLiveData<List<City>> = MutableLiveData()

    init {
        publishSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe {
                searchCityByName(it)
            }
            .add(this)
    }

    fun searchCity(query: String) {
        if (query.isBlank()) cities.value = emptyList()
        publishSubject.onNext(query)
    }

    private fun searchCityByName(query: String) {
        if (query.isBlank()) return
        weatherRepo.searchCitiesByName(query)
            .compose(applyScheduleSingle(loading))
            .subscribe({
                cities.postValue(it)
            }, {
                handleError(it)
            })
            .add(this)
    }

}