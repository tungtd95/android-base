package com.sekiro.data.utils

enum class ErrorType constructor(val errorCode: Int, var errorMessage: String) {

    NETWORK(0x111, "Network Error"),
    DATA(0x222, "Data Error"),
    HTTP(0x333, "HTTP Error"),
    UNKNOWN(BaseError.UNKNOWN_ERROR_CODE, BaseError.UNKNOWN_ERROR_MESSAGE);

    companion object {
        const val HTTP_ERROR_START = 400
        const val HTTP_ERROR_END = 599
    }
}
