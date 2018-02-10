package com.example.yeqf.androidarchitecturecomponents.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.yeqf.androidarchitecturecomponents.persistence.entity.User
import io.reactivex.Flowable

/**
 * Created by yeqf on 2018/2/10.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}