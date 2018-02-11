package me.yeqf.common.utils.rxjava

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2018\2\11 0011.
 */
object RxSchedulers {

    fun <T> runOnIoOfObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

    fun <T> runOnIoOfFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

    fun <T> runOnIoOfSingle(): SingleTransformer<T, T> {
        return SingleTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

    fun runOnIoOfCompletable(): CompletableTransformer {
        return CompletableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

    fun <T> runOnIoOfMaybe(): MaybeTransformer<T, T> {
        return MaybeTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }
}