package me.yeqf.android.persistence.entity

/**
 * Created by Administrator on 2018\2\11 0011.
 */
data class Ganhuo(var _id: String? = "",
                  var createdAt: String? = "",
                  var desc: String? = "",
                  var publishedAt: String? = "",
                  var source: String? = "",
                  var type: String? = "",
                  var url: String? = "",
                  var used: Boolean? = false,
                  var who: String? = "",
                  var images: List<String>? = null){
    constructor() : this("", "", "", "", "", "", "", false, "", null)
}