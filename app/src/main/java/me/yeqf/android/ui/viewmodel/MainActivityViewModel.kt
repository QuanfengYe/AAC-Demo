package me.yeqf.android.ui.viewmodel

import android.util.Log
import com.yeqf.android.base.BaseViewModel
import me.yeqf.android.bean.Category
import me.yeqf.android.bean.GankIo
import me.yeqf.android.persistence.entity.User
import me.yeqf.android.repository.UserRepository
import me.yeqf.common.utils.rxjava.RxSchedulers

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
                .compose(RxSchedulers.runOnIoOfFlowable())
                .subscribe( { body(it) },
                        { error -> Log.e(TAG, "Unable to get user info!", error) }))
    }

    fun addUser(user: User, body: () -> Unit) {
        mDisposable.add(UserRepository.addUser(user)
                .compose(RxSchedulers.runOnIoOfCompletable())
                .subscribe({ body() },
                        { error -> Log.e(TAG, "Unable to update user info!", error) }))

    }

    fun getDaily(year: Int, month: Int, day: Int, body:(GankIo<Category>) -> Unit) {
        mDisposable.add(UserRepository.getDaily(year, month, day)
                .compose(RxSchedulers.runOnIoOfSingle())
                .subscribe( { body(it) }, { error -> Log.e(TAG, "Unable to get gank.io daily  info!", error) }))
    }

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }
}