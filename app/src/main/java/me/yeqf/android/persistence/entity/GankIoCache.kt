package me.yeqf.android.persistence.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.yeqf.android.bean.GanHuo
import me.yeqf.common.utils.TimeUtils
import java.util.*

/**
 * Created by Administrator on 2018\2\12 0012.
 */
@Entity(tableName = "GankIoCache")
data class GankIoCache(@PrimaryKey
                       var _id: String = "",
                       var createdAt: Long = 0,
                       var desc: String? = "",
                       var publishedAt: Long = 0,
                       var source: String? = "",
                       var type: String? = "",
                       var url: String? = "",
                       var used: Boolean? = false,
                       var who: String? = "",
                       var imagesUrl: String? = null,
                       var content: String? = null,
                       var updatedAt: Long = 0,
                       var time: String,
                       var apiFrom: Int,
                       var insertTime: Long) {
    constructor(): this("", 0, "", 0, "", "", "", false, "", "", "", 0, "", 0, 0)
    constructor(o: GanHuo, apiFrom: Int) :
            this(o._id,
                    TimeUtils.getTime(o.createdAt, TimeUtils.FORMAT_YYYYMMDD_T_HHMMSS_SSSZ),
                    o.desc,
                    TimeUtils.getTime(o.publishedAt, TimeUtils.FORMAT_YYYYMMDD_T_HHMMSS_SSSZ),
                    o.source,
                    o.type,
                    o.url,
                    o.used,
                    o.who,
                    null,
                    o.content,
                    TimeUtils.getTime(o.updated_at, TimeUtils.FORMAT_YYYYMMDD_T_HHMMSS_SSSZ),
                    "1970-01-01",
                    apiFrom,
                    0) {
        if(o.images != null) {
            val sb = StringBuffer()
            for (s: String in o.images!!) {
                sb.append(s)
                sb.append(",")
            }
            this.imagesUrl = sb.substring(0, sb.length - 1)
        }
        if(publishedAt > 0)
            this.time = TimeUtils.getTime(publishedAt, TimeUtils.FORMAT_YYYY_MM_DD)
        insertTime = System.currentTimeMillis()
    }
}