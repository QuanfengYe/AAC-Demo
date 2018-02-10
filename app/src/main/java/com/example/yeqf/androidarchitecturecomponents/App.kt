package com.example.yeqf.androidarchitecturecomponents

import android.app.Application

/**
 * Created by yeqf on 2018/2/10.
 */
class App : Application(){

    companion object {
        var mApp: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        mApp = this
    }
}