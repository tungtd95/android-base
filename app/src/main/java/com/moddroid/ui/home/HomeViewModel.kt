package com.moddroid.ui.home

import com.moddroid.data.repo.ModAppRepo
import com.moddroid.data.utils.ErrorHandler
import com.moddroid.ui.base.BaseViewModel

class HomeViewModel(
    private val errorHandler: ErrorHandler,
    private val modAppRepo: ModAppRepo
) : BaseViewModel(errorHandler) {
}