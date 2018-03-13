package me.yeqf.android.persistence.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.common.room.converter.ListConverter

/**
 * Created by yeqf on 2018/2/10.
 */
@Dao
interface GankIoDao {
    /**
     *  time:yyyy-MM-dd
     */
    @Query("SELECT * FROM GankIoCache WHERE time = :time ORDER BY type")
    fun getDailyData(time: String): Flowable<List<GankIoCache>>

    @Query("SELECT * FROM GankIoCache WHERE type = :category ORDER BY publishedAt DESC LIMIT :count OFFSET :offset")
    fun getCategoryData(category: String, count: Int, offset: Int): Flowable<List<GankIoCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: GankIoCache)
}