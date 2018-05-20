package me.yeqf.android

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by yeqf on 2018/2/10.
 */
class App : Application(){

    companion object {
        lateinit var mApp: App

        val uiHandler = Handler(Looper.getMainLooper())

        fun post(runnable: Runnable) {
            uiHandler.post(runnable)
        }
    }

    private lateinit var refWatcher: RefWatcher

    fun getRefWatcher(): RefWatcher = refWatcher

    override fun onCreate() {
        super.onCreate()
        mApp = this
        refWatcher = LeakCanary.install(this);
    }
}