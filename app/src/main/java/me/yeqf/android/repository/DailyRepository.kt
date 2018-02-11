package me.yeqf.android.repository

import me.yeqf.android.persistence.dao.GankIoDao
import me.yeqf.android.persistence.database.CacheDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import me.yeqf.android.api.retrofit.GankIoFactory
import me.yeqf.android.persistence.entity.Category
import me.yeqf.android.persistence.entity.DailyData
import me.yeqf.common.repository.RepositoryHelper

/**
 * Created by yeqf on 2018/2/10.
 */
object DailyRepository {
    private fun getGankIoDao() : GankIoDao {
        return CacheDatabase.getInstance().getGankIoDao()
    }

    fun getDaily(year: Int, month: Int, day: Int): Flowable<DailyData> {

        val helper = object: RepositoryHelper<DailyData, DailyData>() {
            override fun saveCallResult(resp: DailyData) {
                getGankIoDao().insert(resp)
            }

            override fun shouldFetch(data: DailyData?): Boolean {
                if (data == null)
                    return true
                return false
            }

            override fun loadFromDb(): Flowable<DailyData> {
                val time = String.format("%04d%02d%02d", year, month, day)
                return getGankIoDao().getDailyData(time)
            }


            override fun createCall(): Flowable<DailyData> =
                GankIoFactory.getService().getDailyData(year, month, day)

        }

        return helper.getResult()
    }

}