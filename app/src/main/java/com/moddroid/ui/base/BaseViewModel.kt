package com.moddroid.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moddroid.data.utils.ErrorHandler
import com.moddroid.utils.LoadingState
import com.moddroid.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(
    private val errorHandler: ErrorHandler
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val errorMessage = SingleLiveData<String>()
    val networkError = SingleLiveData<String>()
    val loading = MutableLiveData<LoadingState>()

    open fun handleError(throwable: Throwable) {
        val error = errorHandler.parseError(throwable)
        if (error.isNetworkError() || error.isDataError()) {
            networkError.value = error.provideErrorMessage()
        } else {
            errorMessage.value = error.provideErrorMessage()
        }
    }

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
