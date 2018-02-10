package com.example.yeqf.androidarchitecturecomponents.ui.viewmodel

import android.util.Log
import com.example.yeqf.androidarchitecturecomponents.base.BaseViewModel
import com.example.yeqf.androidarchitecturecomponents.persistence.entity.User
import com.example.yeqf.androidarchitecturecomponents.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yeqf on 2018/2/10.
 */
class MainActivityViewModel : BaseViewModel() {

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    fun  getUser(id: String, body: (User) -> Unit)  {
        mDisposable.add(UserRepository.getUserById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { body(it) },
                        { error -> Log.e("", "Unable to get user info!", error) }))
    }

    fun addUser(user: User, body: () -> Unit) {
        mDisposable.add(UserRepository.addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body() },
                        { error -> Log.e("", "Unable to update user info!", error) }))

    }
}