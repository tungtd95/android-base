package com.moddroid.data.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ErrorHandler {

    fun parseError(throwable: Throwable): Error {
        when (throwable) {
            is IOException -> return Error().apply {
                errorInfo = BaseError.ErrorInfo(
                    ErrorType.NETWORK.errorCode,
                    "Network error"
                )
            }
            is JsonSyntaxException -> return Error().apply {
                errorInfo = BaseError.ErrorInfo(
                    ErrorType.DATA.errorCode,
                    "Data error"
                )
            }
            is UnknownHostException -> return Error().apply {
                errorInfo = BaseError.ErrorInfo(
                    ErrorType.NETWORK.errorCode,
                    "Network error"
                )
            }
            is HttpException -> {
                val errorBody: String? = throwable.response()?.errorBody()?.string()
                return try {
                    Gson().fromJson(errorBody, Error::class.java).apply {
                        errorInfo = BaseError.ErrorInfo(
                            provideErrorCode(),
                            this.provideErrorMessage()
                        )
                    }
                } catch (e: JsonSyntaxException) {
                    BaseError.create(Error::class.java, ErrorType.HTTP.apply {
                        errorMessage = "Unknown error"
                    })
                }
            }
            else -> return BaseError.create(Error::class.java, ErrorType.UNKNOWN)
        }
    }
}
