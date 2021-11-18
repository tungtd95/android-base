package com.sekiro.di

import com.sekiro.ui.addcity.AddCityViewModel
import com.sekiro.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { AddCityViewModel(get(), get()) }
}
