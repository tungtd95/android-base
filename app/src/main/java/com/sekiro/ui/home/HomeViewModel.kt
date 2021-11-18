package com.sekiro.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.sekiro.data.models.City
import com.sekiro.data.repo.WeatherRepo
import com.sekiro.data.utils.ErrorHandler
import com.sekiro.ui.base.BaseViewModel
import com.sekiro.utils.applyScheduleFlowable

@SuppressLint("CheckResult")
class HomeViewModel(
    errorHandler: ErrorHandler,
    private val weatherRepo: WeatherRepo
) : BaseViewModel(errorHandler) {

    val cities: MutableLiveData<List<City>> = MutableLiveData()

    init {
        weatherRepo.getCitiesFlow()
            .compose(applyScheduleFlowable())
            .subscribe({
                cities.postValue(it)
            }, {})
    }
}