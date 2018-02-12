package me.yeqf.android

import android.app.Application
import android.os.Handler
import android.os.Looper

/**
 * Created by yeqf on 2018/2/10.
 */
class App : Application(){

    companion object {
        var mApp: App? = null

        val uiHandler = Handler(Looper.getMainLooper())

        fun post(runnable: Runnable) {
            uiHandler.post(runnable)
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApp = this
    }
}