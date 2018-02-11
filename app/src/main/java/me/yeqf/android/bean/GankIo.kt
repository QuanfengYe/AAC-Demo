package me.yeqf.android.bean

/**
 * Created by Administrator on 2018\2\11 0011.
 */
data class GankIo<out T>(val count: Int = 0,
                         val category: List<String>? = null,
                         val error: Boolean = false,
                         val results: T? = null)