package me.yeqf.android.ui.viewmodel

import android.util.Log
import com.yeqf.android.base.BaseViewModel
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.repository.GankIoRepository
import me.yeqf.common.utils.rxjava.RxSchedulers

/**
 * Created by yeqf on 2018/2/10.
 */
class CategoryViewModel : BaseViewModel() {

    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    fun getCategory(category: String, count: Int, page: Int, body:(List<GankIoCache>) -> Unit) {
        getCategory(category, count, page, false, body)
    }

    fun getCategory(category: String, count: Int, page: Int, reLoad: Boolean, body:(List<GankIoCache>) -> Unit) {
        mDisposable.add(GankIoRepository.getCategory(category, count, page, reLoad)
                .compose(RxSchedulers.Flowable.runOnIo())
                .subscribe({ body(it) }, { Log.e(TAG, "Unable to get gank.io category  info!", it) }))
    }

    companion object {
        private val TAG = CategoryViewModel::class.java.simpleName
    }
}