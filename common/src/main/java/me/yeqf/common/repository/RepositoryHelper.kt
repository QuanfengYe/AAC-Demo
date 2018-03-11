package me.yeqf.common.repository

import io.reactivex.Flowable
import me.yeqf.common.utils.rxjava.RxSchedulers

/**
 - 
 - @Author:  yeqf
 - @Time:  2018/3/11 23:09
 */
abstract class RepositoryHelper<ResultType, ResponseType> {
    fun getResult(): Flowable<ResultType> =
            loadLocalCache()
                    .doOnNext {
                        if (isShouldFetch(it))
                            loadNetData().compose(RxSchedulers.Flowable.runOnlyIo())
                                    .subscribe {
                                        saveNetData(it)
                                    }
                    }.filter {
                        return@filter isEmitCache(it)
                    }.map { return@map makeExtraWork(it) }

    /**
     * 是否发射数据
     * @param data 数据库查询返回信息
     * @return Boolean
     */
    protected abstract fun isEmitCache(data: ResultType?): Boolean

    /**
     *
     */
    protected abstract fun isShouldFetch(data: ResultType?): Boolean

    protected abstract fun loadLocalCache(): Flowable<ResultType>

    protected abstract fun loadNetData(): Flowable<ResponseType>

    protected abstract fun saveNetData(resp: ResponseType)

    protected abstract fun makeExtraWork(data: ResultType): ResultType

}
