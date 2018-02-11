package me.yeqf.android.persistence.entity

/**
 * Created by Administrator on 2018\2\11 0011.
 */
data class Category(var Android: List<Ganhuo>? = null,
                    var iOS: List<Ganhuo>? = null,
                    var 休息视频: List<Ganhuo>? = null,
                    var 拓展资源: List<Ganhuo>? = null,
                    var 瞎推荐: List<Ganhuo>? = null,
                    var 福利: List<Ganhuo>? = null,
                    var App: List<Ganhuo>? = null,
                    var 前端: List<Ganhuo>? = null) {
    constructor(): this(null, null, null, null, null, null, null, null)
}