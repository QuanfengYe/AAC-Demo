package me.yeqf.android.bean

/**
 * Created by Administrator on 2018\2\11 0011.
 */
data class Category(val Android: List<Ganhuo>,
                    val iOS: List<Ganhuo>,
                    val 休息视频: List<Ganhuo>,
                    val 拓展资源: List<Ganhuo>,
                    val 瞎推荐: List<Ganhuo>,
                    val 福利: List<Ganhuo>,
                    val App: List<Ganhuo>,
                    val 前端: List<Ganhuo>)