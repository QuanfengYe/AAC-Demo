package com.example.yeqf.androidarchitecturecomponents.persistence.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.example.yeqf.androidarchitecturecomponents.App
import com.example.yeqf.androidarchitecturecomponents.persistence.dao.UserDao
import com.example.yeqf.androidarchitecturecomponents.persistence.entity.User

/**
 * Created by yeqf on 2018/2/10.
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile private var INSTANCE : UserDatabase? = null

        fun getInstance(): UserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase().also { INSTANCE = it }
            }

        private fun buildDatabase(): UserDatabase =
                Room.databaseBuilder(App.mApp!!.applicationContext, UserDatabase::class.java, "user.db").build()
    }
}