package com.moddroid.data.repo

import com.moddroid.data.models.ModApp
import com.moddroid.data.service.ModAppService
import io.reactivex.Single

class ModAppRepo(private val service: ModAppService) {

    fun getModApps(): Single<ModApp> {
        return service.getModApp().map { it.data }
    }
}