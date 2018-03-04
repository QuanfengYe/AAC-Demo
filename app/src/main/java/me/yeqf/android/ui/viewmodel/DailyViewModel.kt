package me.yeqf.android.ui.viewmodel

import android.util.Log
import com.yeqf.android.base.BaseViewModel
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.repository.GankIoRepository
import me.yeqf.common.utils.rxjava.RxSchedulers

/**
 * Created by yeqf on 2018/2/10.
 */
class DailyViewModel : BaseViewModel() {

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    fun getDate(body:(String) -> Unit) {
        mDisposable.add(GankIoRepository.getPostedDateList()
                .compose(RxSchedulers.Flowable.runOnIo())
                .subscribe( {body(it.results[0]) }, { Log.e(TAG, "Unable to get gank.io posted date info!", it) }))
    }

    fun getDaily(date: Array<Int>, body:(List<GankIoCache>) -> Unit) {
        mDisposable.add(GankIoRepository.getDaily(date)
                .compose(RxSchedulers.Flowable.runOnIo())
                .subscribe( { body(it) }, { Log.e(TAG, "Unable to get gank.io daily  info!", it) }))
    }

    fun getCategory(category: String, count: Int, page: Int, body:(GankIoCache) -> Unit) {
        mDisposable.add(GankIoRepository.getCatetory(category, count, page)
                .compose(RxSchedulers.Flowable.runOnIo())
                .subscribe({ body(it) }, { Log.e(TAG, "Unable to get gank.io category  info!", it) }))
    }

    companion object {
        private val TAG = DailyViewModel::class.java.simpleName
    }
}