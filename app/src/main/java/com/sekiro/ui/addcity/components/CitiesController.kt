package com.sekiro.ui.addcity.components

import com.airbnb.epoxy.EpoxyController
import com.sekiro.data.models.City

class CitiesController(
    private val listener: Listener
) : EpoxyController() {

    var cities: List<City> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        cities.forEach {
            cityItem {
                id(CityItem.ID, it.getFullName())
                city(it)
                onClickCityListener {
                    listener.onClickCity(it)
                }
            }
        }
    }

    interface Listener {
        fun onClickCity(city: City)
    }
}