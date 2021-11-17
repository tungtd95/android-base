package com.sekiro.data.service

import com.sekiro.data.models.Weather
import com.sekiro.data.models.response.Response
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherService {

    @GET("/get-mod-app")
    fun getModApp(): Single<Response<Weather>>
}