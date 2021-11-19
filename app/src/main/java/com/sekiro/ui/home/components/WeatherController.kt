package com.sekiro.ui.home.components

import com.airbnb.epoxy.EpoxyController
import com.sekiro.data.models.City
import com.sekiro.data.models.Weather

class WeatherController(
    private val listener: Listener
) : EpoxyController() {

    var weathers: List<Pair<City, Weather>>? = null
        set(value) {
            field = value
            requestModelBuild()
        }
    var loading: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (loading) loadingItem { id(LoadingItem.ID) }
        weathers?.forEach {
            weatherItem {
                id(WeatherItem.ID, it.first.getFullName(), it.second.main.toString())
                weather(it)
                onRemove { listener.onRemove(it.first) }
            }
        }
    }

    interface Listener {
        fun onRemove(city: City)
    }
}