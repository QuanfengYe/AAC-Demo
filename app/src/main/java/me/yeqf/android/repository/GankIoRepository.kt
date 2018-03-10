package me.yeqf.android.repository

import android.util.Log
import io.reactivex.Flowable
import me.yeqf.android.api.retrofit.GankIoFactory
import me.yeqf.android.bean.DailyData
import me.yeqf.android.bean.DateData
import me.yeqf.android.bean.GanHuo
import me.yeqf.android.bean.ItemData
import me.yeqf.android.persistence.dao.GankIoDao
import me.yeqf.android.persistence.database.CacheDatabase
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.common.repository.RepositoryHelper

/**
 * Created by yeqf on 2018/2/10.
 */
object GankIoRepository {
    val TAG = GankIoRepository::class.java.simpleName

    private fun getGankIoDao(): GankIoDao {
        return CacheDatabase.getInstance().getGankIoDao()
    }

    fun getPostedDateList(): Flowable<DateData> {
        return GankIoFactory.getService().getPostedDateList()
    }

    fun getDaily(date: Array<Int>): Flowable<List<GankIoCache>> {
        Log.d(TAG, "getDaily")

        val helper = object : RepositoryHelper<List<GankIoCache>, DailyData>() {
            override fun saveCallResult(resp: DailyData) {
                Log.d(TAG, "getDaily saveCallResult")
                val map = resp.results
                if (map != null) {
                    CacheDatabase.getInstance().beginTransaction()
                    for (o: GanHuo in map.getMergeList()) {
                        save(o)
                    }
                    CacheDatabase.getInstance().setTransactionSuccessful()
                    CacheDatabase.getInstance().endTransaction()
                }
            }

            override fun shouldFetch(data: List<GankIoCache>?): Boolean {
                Log.d(TAG, "getDaily shouldFetch")
                if (data == null || data.isEmpty())
                    return true
                for (it: GankIoCache in data) {
                    if (System.currentTimeMillis() - it.insertTime < 8 * 60 * 60 * 1000)
                        return false
                }
                return true
            }

            override fun loadFromDb(): Flowable<List<GankIoCache>> {
                Log.d(TAG, "getDaily loadFromDb")
                val time = String.format("%04d-%02d-%02d", date[0], date[1], date[2])
                return getGankIoDao().getDailyData(time)
            }

            override fun createCall(): Flowable<DailyData> {
                Log.d(TAG, "getDaily createCall")
                return GankIoFactory.getService().getDailyData(date[0], date[1], date[2])
            }

        }

        return helper.getResult()
    }

    fun getCatetory(category: String, count: Int, page: Int): Flowable<List<GankIoCache>> {
        Log.d(TAG, "getCatetory")

        val helper = object : RepositoryHelper<List<GankIoCache>, ItemData>() {
            override fun saveCallResult(resp: ItemData) {
                Log.d(TAG, "getCatetory saveCallResult")
                val list = resp.results
                if (list != null) {
                    CacheDatabase.getInstance().beginTransaction()
                    for (o: GanHuo in list) {
                        save(o)
                    }
                    CacheDatabase.getInstance().setTransactionSuccessful()
                    CacheDatabase.getInstance().endTransaction()
                }
            }

            override fun shouldFetch(data: List<GankIoCache>?): Boolean {
                Log.d(TAG, "getCatetory shouldFetch")
                if (data == null || data.isEmpty())
                    return true
                for (it: GankIoCache in data) {
                    if (System.currentTimeMillis() - it.insertTime < 8 * 60 * 60 * 1000)
                        return false
                }
                return true
            }

            override fun loadFromDb(): Flowable<List<GankIoCache>> {
                Log.d(TAG, "getCatetory loadFromDb")
                val offset = (page - 1) * count
                return getGankIoDao().getCategoryData(category, count, offset)
            }

            override fun createCall(): Flowable<ItemData> {
                Log.d(TAG, "getCatetory createCall")
                return GankIoFactory.getService().getCategoryData(category, count, page)
            }

        }

        return helper.getResult()
    }

    private fun save(o: GanHuo) {
        val cache = GankIoCache(o)
        Log.d(TAG, "save " + cache.toString())

        getGankIoDao().insert(cache)
    }
}