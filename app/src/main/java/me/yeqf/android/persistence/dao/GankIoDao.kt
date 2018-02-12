package me.yeqf.android.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import me.yeqf.android.bean.DailyData
import me.yeqf.android.persistence.entity.GankIoCache

/**
 * Created by yeqf on 2018/2/10.
 */
@Dao
interface GankIoDao {
    /**
     *  time:yyyy-MM-dd
     */
    @Query("SELECT * FROM GankIoCache WHERE time = :time")
    fun getDailyData(time: String): Flowable<GankIoCache>

    @Query("SELECT * FROM GankIoCache WHERE type = :category LIMIT :count OFFSET :offset")
    fun getCategoryData(category: String, count: Int, offset: Int): Flowable<GankIoCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: GankIoCache)
}