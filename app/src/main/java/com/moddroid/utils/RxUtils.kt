package com.moddroid.utils

import androidx.lifecycle.MutableLiveData
import com.moddroid.ui.base.BaseViewModel
import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Disposable.add(viewModel: BaseViewModel) {
    viewModel.add(this)
}

fun <T> applyScheduleObservable(
    loading: MutableLiveData<LoadingState>? = null
): ObservableTransformer<T, T> =
    ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading?.postValue(LoadingState(true)) }
            .doAfterTerminate { loading?.postValue(LoadingState(false)) }
    }

fun <T> applyScheduleSingle(
    loading: MutableLiveData<LoadingState>? = null
): SingleTransformer<T, T> =
    SingleTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading?.postValue(LoadingState(true)) }
            .doOnSuccess { loading?.postValue(LoadingState(false)) }
            .doAfterTerminate { loading?.postValue(LoadingState(false)) }
    }

fun applyScheduleCompletable(): CompletableTransformer =
    CompletableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

class LoadingState(val isLoading: Boolean)