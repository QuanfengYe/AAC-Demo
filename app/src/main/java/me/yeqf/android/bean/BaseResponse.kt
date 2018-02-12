package me.yeqf.android.bean

/**
 * Created by Administrator on 2018\2\12 0012.
 */
open class BaseResponse(var count: Int = 0,
                        var category: List<String>? = null,
                        var error: Boolean = false)