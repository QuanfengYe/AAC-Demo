package me.yeqf.android.ui.viewmodel

import android.util.Log
import com.yeqf.android.base.BaseViewModel
import me.yeqf.android.persistence.entity.DailyData
import me.yeqf.android.repository.DailyRepository
import me.yeqf.common.utils.rxjava.RxSchedulers

/**
 * Created by yeqf on 2018/2/10.
 */
class MainActivityViewModel : BaseViewModel() {

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    fun getDaily(year: Int, month: Int, day: Int, body:(DailyData) -> Unit) {
        mDisposable.add(DailyRepository.getDaily(year, month, day)
                .compose(RxSchedulers.runOnIoOfFlowable())
                .subscribe( { body(it) }, { error -> Log.e(TAG, "Unable to get gank.io daily  info!", error) }))
    }

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }
}