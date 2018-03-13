package me.yeqf.android.utils

/**
 *
 * @Author yeqf
 * @Time 2018/3/13 19:30
 */
object SitesHelper {
    private val sites = mapOf<String, String>(
            "github.com" to "GitHub",
            "jianshu.com" to "简书",
            "bilibili.com" to "B站",
            "weixin.qq.com" to "微信分享",
            "weibo.cn" to "微博",
            "weibo.com" to "微博",
            "zhihu.com" to "知乎",
            "miaopai.com" to "秒拍"
    )

    fun getSiteName(url: String?): String? {
        if (url != null) {
            val hosts = sites.keys
            for (it in hosts) {
                if (url.contains(it)) {
                    return sites[it]
                }
            }
        }
        return "其他"
    }
}