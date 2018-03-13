package me.yeqf.common.room.converter

import android.arch.persistence.room.TypeConverter

/**
 *
 * @Author yeqf
 * @Time 2018/3/13 11:52
 */
class ListConverter {

    @TypeConverter fun stringsToList(str: String): List<String> {
        return str.split("\\s*,\\s*")
    }
    
    @TypeConverter fun listToStrings(data: List<String>?): String {
        if(data == null)
            return ""
        val result = StringBuffer()
        for (it in data) {
            result.append("$it,")
        }
        return result.toString()
    }
}