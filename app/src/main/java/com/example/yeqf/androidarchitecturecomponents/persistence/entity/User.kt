package com.example.yeqf.androidarchitecturecomponents.persistence.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by yeqf on 2018/2/10.
 */
@Entity(tableName = "users")
data class User(@PrimaryKey
                @ColumnInfo(name = "id")
                val id: String = UUID.randomUUID().toString(),
                @ColumnInfo(name = "name")
                val name: String)