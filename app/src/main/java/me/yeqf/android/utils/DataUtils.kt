package me.yeqf.android.utils

import me.yeqf.android.library.brvah.bean.GankIoSection
import me.yeqf.android.persistence.entity.GankIoCache
import java.util.*

/**
 * Created by yeqf on 2018/3/3.
 */
object DataUtils {
    fun getSectionData(data: List<GankIoCache>): MutableList<GankIoSection> {
        val mData: MutableList<GankIoSection> = arrayListOf()
        val flags = arrayOfNulls<Boolean>(7)
//        Collections.sort(data)
        for (obj: GankIoCache in data) {
            when (obj.type) {
                "Android" -> {
                    if (flags[0] == true) {
                        mData.add(GankIoSection(obj))
                    } else {
                        mData.add(GankIoSection(true, obj.type!!))
                        flags[0] = true
                        mData.add(GankIoSection(obj))
                    }
                }
                "iOS" -> {
                    if (flags[1] == true) {
                        mData.add(GankIoSection(obj))
                    } else {
                        mData.add(GankIoSection(true, obj.type!!))
                        flags[1] = true
                        mData.add(GankIoSection(obj))
                    }
                }
                "休息视频" -> {
                    if (flags[2] == true) {
                        mData.add(GankIoSection(obj))
                    } else {
                        mData.add(GankIoSection(true, obj.type!!))
                        flags[2] = true
                        mData.add(GankIoSection(obj))
                    }
                }
                "拓展资源" -> {
                    if (flags[3] == true) {
                        mData.add(GankIoSection(obj))
                    } else {
                        mData.add(GankIoSection(true, obj.type!!))
                        flags[3] = true
                        mData.add(GankIoSection(obj))
                    }
                }
                "前端" -> {
                    if (flags[4] == true) {
                        mData.add(GankIoSection(obj))
                    } else {
                        mData.add(GankIoSection(true, obj.type!!))
                        flags[4] = true
                        mData.add(GankIoSection(obj))
                    }
                }
                "瞎推荐" -> {
                    if (flags[5] == true) {
                        mData.add(GankIoSection(obj))
                    } else {
                        mData.add(GankIoSection(true, obj.type!!))
                        flags[5] = true
                        mData.add(GankIoSection(obj))
                    }
                }
                "App" -> {
                    if (flags[6] == true) {
                        mData.add(GankIoSection(obj))
                    } else {
                        mData.add(GankIoSection(true, obj.type!!))
                        flags[6] = true
                        mData.add(GankIoSection(obj))
                    }
                }
            }
        }
        return mData
    }
}