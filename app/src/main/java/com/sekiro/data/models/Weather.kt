package com.sekiro.data.models

import com.google.gson.annotations.SerializedName

data class Weather(
    val main: WeatherMain?,
    @SerializedName("weather")
    val weathers: List<WeatherInfo>?
)

data class WeatherMain(
    val temp: Float? = null,
    val pressure: Float? = null,
    val humidity: Float? = null,
)

data class WeatherInfo(
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
) {
    fun getWeatherIconUrl(): String =
        if (icon.isNullOrBlank()) {
            ""
        } else {
            "https://openweathermap.org/img/wn/$icon@2x.png"
        }
}
