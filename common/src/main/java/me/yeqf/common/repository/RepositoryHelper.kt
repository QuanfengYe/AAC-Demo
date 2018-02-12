package me.yeqf.common.repository

import io.reactivex.Flowable

/**
 *
 */
abstract class RepositoryHelper<ResultType, ResponseType> {
    private var response: ResponseType? = null
    private var result: ResultType? = null

    fun getResult(): Flowable<ResultType> =
            Flowable.concat(
                    loadFromDb(),
                    createCall().concatMap {
                                saveCallResult(it)
                                return@concatMap loadFromDb()
                            }
            )


    protected abstract fun saveCallResult(resp: ResponseType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun createCall(): Flowable<ResponseType>

}
