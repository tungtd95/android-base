package com.moddroid.data.models.response

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("meta")
    val meta: Meta?
)

data class EmptyResponse(
    @SerializedName("meta")
    val meta: Meta?
)

data class Meta(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?
)
