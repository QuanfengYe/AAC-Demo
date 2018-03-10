package me.yeqf.common.utils

import android.content.Context

/**
 * Created by yeqf on 2018/3/6.
 */
object Tools {
    fun dp2px(context: Context, value: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (value * scale + 0.5f).toInt()
    }

    fun px2dp(context: Context, value: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (value / scale + 0.5f).toInt()
    }
}