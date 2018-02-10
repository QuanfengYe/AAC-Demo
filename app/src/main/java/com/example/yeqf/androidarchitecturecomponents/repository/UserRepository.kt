package com.example.yeqf.androidarchitecturecomponents.repository

import com.example.yeqf.androidarchitecturecomponents.persistence.dao.UserDao
import com.example.yeqf.androidarchitecturecomponents.persistence.database.UserDatabase
import com.example.yeqf.androidarchitecturecomponents.persistence.entity.User
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by yeqf on 2018/2/10.
 */
object UserRepository {
    private fun getUserDao() : UserDao {
        return UserDatabase.getInstance().getUserDao()
    }

    fun getUserById(id: String): Flowable<User> =
            getUserDao().getUserById(id)

    fun addUser(user: User): Completable =
        Completable.fromAction( {
            getUserDao().insert(user)
        })
}