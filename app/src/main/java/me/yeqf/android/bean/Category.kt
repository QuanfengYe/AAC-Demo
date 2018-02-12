package me.yeqf.android.bean

/**
 * Created by Administrator on 2018\2\11 0011.
 */
data class Category(
                    var Android: List<GanHuo>? = null,
                    var iOS: List<GanHuo>? = null,
                    var 休息视频: List<GanHuo>? = null,
                    var 拓展资源: List<GanHuo>? = null,
                    var 瞎推荐: List<GanHuo>? = null,
                    var 福利: List<GanHuo>? = null,
                    var App: List<GanHuo>? = null,
                    var 前端: List<GanHuo>? = null) {
    fun getMergeList() : List<GanHuo> {
        val results = mutableListOf<GanHuo>()
        if(Android != null)
            results.addAll(Android!!)
        if(iOS != null)
            results.addAll(iOS!!)
        if(休息视频 != null)
            results.addAll(休息视频!!)
        if(拓展资源 != null)
            results.addAll(拓展资源!!)
        if(瞎推荐 != null)
            results.addAll(瞎推荐!!)
        if(福利 != null)
            results.addAll(福利!!)
        if(App != null)
            results.addAll(App!!)
        if(前端 != null)
            results.addAll(前端!!)
        return results
    }
}