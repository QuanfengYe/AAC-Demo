package me.yeqf.android.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import me.yeqf.android.persistence.entity.DailyData

/**
 * Created by yeqf on 2018/2/10.
 */
@Dao
interface GankIoDao {
    /**
     *  time:yyyy-MM-dd
     */
    @Query("SELECT * FROM DailyCache WHERE time = :time")
    fun getDailyData(time: String): Flowable<DailyData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DailyData)
}