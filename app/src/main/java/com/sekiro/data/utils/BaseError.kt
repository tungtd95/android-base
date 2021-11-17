package com.sekiro.data.utils

import java.io.Serializable

abstract class BaseError : Serializable {

    companion object {
        const val UNKNOWN_ERROR_CODE = 0x999
        const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
        const val HTTP_ERROR_CODE_UNAUTHORIZED = 401

        fun <T : BaseError> create(clazz: Class<T>, errorType: ErrorType): T {
            val instance = clazz.newInstance()
            instance.errorInfo = ErrorInfo(errorType.errorCode, errorType.errorMessage)
            return instance
        }
    }

    var errorInfo: ErrorInfo? = null

    abstract fun provideErrorCode(): Int

    abstract fun provideErrorMessage(): String

    fun isNetworkError(): Boolean {
        return provideErrorCode() == ErrorType.NETWORK.errorCode
    }

    fun isDataError(): Boolean {
        return provideErrorCode() == ErrorType.DATA.errorCode
    }

    fun isHttpError(): Boolean {
        return (provideErrorCode() in ErrorType.HTTP_ERROR_START..ErrorType.HTTP_ERROR_END)
    }

    fun isUnknownError(): Boolean {
        return provideErrorCode() == ErrorType.UNKNOWN.errorCode
    }

    data class ErrorInfo(val errorCode: Int, val errorMessage: String?) : Serializable
}
