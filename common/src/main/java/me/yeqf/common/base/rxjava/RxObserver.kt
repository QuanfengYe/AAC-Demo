//package me.yeqf.common.base.rxjava
//
//import io.reactivex.Observer
//import io.reactivex.disposables.Disposable
//
///**
// * Created by Administrator on 2018\2\11 0011.
// */
//abstract class RxObserver<T>: Observer<T> {
//
//    override fun onSubscribe(d: Disposable?) {
//
//    }
//    override fun onNext(value: T) {
//        /**
//         * TODO:
//         * if() onSuccess(value)
//         * else onFailure(code, msg)
//         */
//    }
//
//    override fun onError(e: Throwable?) {
//        RxError.onError(e)
//    }
//
//    override fun onComplete() {
//
//    }
//
//    abstract fun onSuccess(t: T)
//
//    abstract fun onFailure(code: Int, msg: String)
//}