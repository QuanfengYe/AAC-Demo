package me.yeqf.common.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yeqf on 2018/2/12.
 */
object TimeUtils {
    val FORMAT_YYYYMMDD = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
    val FORMAT_YYYY_MM_DD = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    val FORMAT_YYYYMMDD_T_HHMMSS_SSSZ = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    /**
     * 将字符串时间转long值（ms）
     * @param time 时间格式字符串
     * @param format time的时间格式
     * @return Long
     */
    fun getTime(time: String?, format: SimpleDateFormat): Long {
        if(time == null)
            return 0
        try {
            val date = format.parse(time)
            Log.d("", "date:" + date.toString() + ", time:" + date.time)
            return date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 将long值（ms）转字符串时间
     * @param time Long
     * @param format time的时间格式
     * @return String
     */
    fun getTime(time: Long, format: SimpleDateFormat): String {
        try {
            return format.format(Date(time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDate(time: String, format: SimpleDateFormat): Array<Int> {
        val array = arrayOf(0, 0, 0)
        val c = Calendar.getInstance()
        c.clear()
        c.timeInMillis = getTime(time, format)
        array[0] = c.get(Calendar.YEAR)
        array[1] = c.get(Calendar.MONTH) + 1
        array[2] = c.get(Calendar.DAY_OF_MONTH)
        return array
    }
}