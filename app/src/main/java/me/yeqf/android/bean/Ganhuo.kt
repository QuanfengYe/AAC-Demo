package me.yeqf.android.bean

/**
 * Created by Administrator on 2018\2\11 0011.
 */
data class Ganhuo(val _id: String,
                  val createdAt: String,
                  val desc: String,
                  val publishedAt: String,
                  val source: String,
                  val type: String,
                  val url: String,
                  val used: Boolean,
                  val who: String,
                  val images: List<String>)