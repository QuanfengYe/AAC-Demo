package me.yeqf.android.ui.viewmodel

import android.util.Log
import com.yeqf.android.base.BaseViewModel
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.repository.GankIoRepository
import me.yeqf.android.utils.DataUtils
import me.yeqf.common.utils.TimeUtils
import me.yeqf.common.utils.rxjava.RxSchedulers

/**
 * Created by yeqf on 2018/2/10.
 */
class DailyViewModel : BaseViewModel() {

    private var mTodayTime: String? = null

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    fun getDate(body:(String, List<GankIoCache>) -> Unit) {
        mDisposable.add(GankIoRepository.getPostedDateList()
                .compose(RxSchedulers.Flowable.runOnIo())
                .subscribe( {
                    requestDailyData(it.results[0], body)
                    mTodayTime = it.results[0]
                }, { Log.e(TAG, "Unable to get gank.io posted date info!", it) }))
    }

    private fun requestDailyData(time: String, body:(String, List<GankIoCache>) -> Unit) {
        val date = TimeUtils.getDateArray(time, TimeUtils.FORMAT_YYYY_MM_DD)
        getDaily(date, body)
    }

    fun getDaily(date: Array<Int>, body:(String, List<GankIoCache>) -> Unit) {
        getDaily(date, false, body)
    }

    fun getDaily(date: Array<Int>, reLoad: Boolean, body:(String, List<GankIoCache>) -> Unit) {
        mDisposable.add(GankIoRepository.getDaily(date, reLoad)
                .compose(RxSchedulers.Flowable.runOnIo())
                .subscribe( { mTodayTime?.run { body(this, it) } }, { Log.e(TAG, "Unable to get gank.io daily  info!", it) }))
    }

    companion object {
        private val TAG = DailyViewModel::class.java.simpleName
    }
}