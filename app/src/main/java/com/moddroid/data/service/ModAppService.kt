package com.moddroid.data.service

import com.moddroid.data.models.ModApp
import com.moddroid.data.models.response.Response
import io.reactivex.Single
import retrofit2.http.GET

interface ModAppService {

    @GET("/get-mod-app")
    fun getModApp(): Single<Response<ModApp>>
}