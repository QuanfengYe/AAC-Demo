package me.yeqf.common.repository

import io.reactivex.Flowable
import me.yeqf.common.utils.rxjava.RxSchedulers

/**
 *
 */
abstract class RepositoryHelper<ResultType, ResponseType> {

    fun getResult(): Flowable<ResultType> =
            loadFromDb().doOnNext {
                if (shouldFetch(it))
                    createCall().compose(RxSchedulers.Flowable.runOnlyIo())
                            .subscribe {
                                saveCallResult(it)
                            }
            }

    protected abstract fun saveCallResult(resp: ResponseType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun createCall(): Flowable<ResponseType>

}
