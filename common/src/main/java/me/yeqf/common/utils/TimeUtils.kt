package me.yeqf.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yeqf on 2018/2/12.
 */
object TimeUtils {
    val FORMAT_YYYYMMDD = SimpleDateFormat("yyyyMMdd", Locale.CHINA)

    fun getTime(time: String, format: SimpleDateFormat): Long {
        try {
            val date = format.parse(time)
            return date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
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