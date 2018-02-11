package me.yeqf.common.repository

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 *
 */
abstract class RepositoryHelper<ResultType, ResponseType> {
    private var response: ResponseType? = null
    private var result: ResultType? = null

    fun getResult(): Flowable<ResultType> =
        Flowable.create({ e ->
            val db = loadFromDb()
            db.subscribeOn(Schedulers.io())
                .flatMap(Function<ResultType, Flowable<ResponseType>> { data ->
                    if(shouldFetch(data)) {
                        return@Function createCall()
                    }
                    return@Function null
                }).flatMap(Function<ResponseType, Flowable<ResultType>> { data ->
                    if(data != null) {
                        saveCallResult(data)
                        return@Function loadFromDb()
                    }
                    return@Function null
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    e.onNext(it)
                    e.onComplete()
                }
        }, BackpressureStrategy.ERROR)


    protected abstract fun saveCallResult(resp: ResponseType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun createCall(): Flowable<ResponseType>

}
