package com.sekiro.ui.home

import com.sekiro.data.repo.WeatherRepo
import com.sekiro.data.utils.ErrorHandler
import com.sekiro.ui.base.BaseViewModel

class HomeViewModel(
    private val errorHandler: ErrorHandler,
    private val weatherRepo: WeatherRepo
) : BaseViewModel(errorHandler) {
}