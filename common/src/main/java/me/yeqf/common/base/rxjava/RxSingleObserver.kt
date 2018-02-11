//package me.yeqf.common.base.rxjava
//
//import io.reactivex.SingleObserver
//import io.reactivex.disposables.Disposable
//
///**
// * Created by Administrator on 2018\2\11 0011.
// */
//abstract class RxSingleObserver<T> : SingleObserver<T> {
//    override fun onSubscribe(d: Disposable?) {
//
//    }
//
//    override fun onError(e: Throwable?) {
//        RxError.onError(e)
//    }
//}