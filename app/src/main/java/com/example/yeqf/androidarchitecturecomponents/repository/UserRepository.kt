package com.example.yeqf.androidarchitecturecomponents.repository

import android.arch.lifecycle.LiveData
import com.example.yeqf.androidarchitecturecomponents.persistence.dao.UserDao
import com.example.yeqf.androidarchitecturecomponents.persistence.database.UserDatabase
import com.example.yeqf.androidarchitecturecomponents.persistence.entity.User

/**
 * Created by yeqf on 2018/2/10.
 */
object UserRepository {
    private fun getUserDao() : UserDao {
        return UserDatabase.getInstance().getUserDao()
    }

    fun getUserById(id: String): LiveData<User> =
            getUserDao().getUserById(id)

    fun addUser(user: User) {
        getUserDao().insert(user)
    }
}