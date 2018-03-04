package me.yeqf.android.bean

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Administrator on 2018\2\11 0011.
 */
data class DateData(var results: List<String>) : BaseResponse()