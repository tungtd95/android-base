package com.moddroid.data.utils

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Error : BaseError() {
    @SerializedName("meta")
    @Expose
    var meta: Meta? = null

    @Keep
    open class Meta {
        @SerializedName("code")
        @Expose
        var code: Int? = null

        @SerializedName("message")
        @Expose
        var message: String? = null
    }

    override fun provideErrorCode(): Int {
        return errorInfo?.errorCode ?: meta?.code ?: UNKNOWN_ERROR_CODE
    }

    override fun provideErrorMessage(): String {
        return errorInfo?.errorMessage ?: meta?.message ?: UNKNOWN_ERROR_MESSAGE
    }
}
