package me.yeqf.android.persistence.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import me.yeqf.android.App
import me.yeqf.android.persistence.dao.GankIoDao
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.common.room.converter.ListConverter

/**
 * Created by yeqf on 2018/2/10.
 */
@Database(entities = arrayOf(GankIoCache::class), version = 1)
@TypeConverters(ListConverter::class)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun getGankIoDao(): GankIoDao

    companion object {
        @Volatile private var INSTANCE: CacheDatabase? = null

        fun getInstance(): CacheDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase().also { INSTANCE = it }
                }

        private fun buildDatabase(): CacheDatabase =
                Room.databaseBuilder(App.mApp!!.applicationContext, CacheDatabase::class.java,
                        "Cache.db").build()
    }
}