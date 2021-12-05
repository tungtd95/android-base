package com.sekiro.di

import com.sekiro.ui.sample.addcity.AddCityViewModel
import com.sekiro.ui.sample.htmlviewer.HtmlViewerViewModel
import com.sekiro.ui.base.DummyViewModel
import com.sekiro.ui.sample.citydetails.CityDetailsViewModel
import com.sekiro.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { AddCityViewModel(get(), get()) }
    viewModel { CityDetailsViewModel(get(), get()) }
    viewModel { DummyViewModel(get()) }
    viewModel { HtmlViewerViewModel(get()) }
}
