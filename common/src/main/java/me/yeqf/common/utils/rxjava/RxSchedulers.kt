package me.yeqf.common.utils.rxjava

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2018\2\11 0011.
 */
object RxSchedulers {

    object Observable {
        fun <T> runOnlyIo(): ObservableTransformer<T, T> {
            return ObservableTransformer { it.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
        }

        fun <T> runOnIo(): ObservableTransformer<T, T> {
            return ObservableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }
    }

    object Flowable {
        fun <T> runOnlyIo(): FlowableTransformer<T, T> {
            return FlowableTransformer { it.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
        }

        fun <T> runOnIo(): FlowableTransformer<T, T> {
            return FlowableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }
    }

    object Single {
        fun <T> runOnlyIo(): SingleTransformer<T, T> {
            return SingleTransformer { it.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
        }

        fun <T> runOnIo(): SingleTransformer<T, T> {
            return SingleTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }
    }

    object Completable {
        fun runOnlyIo(): CompletableTransformer {
            return CompletableTransformer { it.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
        }

        fun runOnIo(): CompletableTransformer {
            return CompletableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }
    }

    object Maybe {
        fun <T> runOnlyIo(): MaybeTransformer<T, T> {
            return MaybeTransformer { it.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
        }

        fun <T> runOnIo(): MaybeTransformer<T, T> {
            return MaybeTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }
    }
}