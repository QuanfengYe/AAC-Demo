package com.example.yeqf.androidarchitecturecomponents.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.yeqf.androidarchitecturecomponents.persistence.entity.User
import com.example.yeqf.androidarchitecturecomponents.repository.UserRepository

/**
 * Created by yeqf on 2018/2/10.
 */
class MainActivityViewModel : ViewModel() {

    fun getUser(id: String): LiveData<User> =
        UserRepository.getUserById(id)

    fun addUser(user: User) {
        Thread(Runnable {
            UserRepository.addUser(user)
        }).start()
    }
}