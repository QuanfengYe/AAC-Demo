package com.yeqf.android.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by yeqf on 2018/2/10.
 */
open class BaseViewModel : ViewModel(), BaseLifecycleObserver{

    val mDisposable = CompositeDisposable()

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}