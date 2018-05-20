package me.yeqf.android.ui.adapter.recyclerview

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.yeqf.android.R
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.utils.SitesHelper
import me.yeqf.common.utils.ShapeUtils
import me.yeqf.common.utils.TimeUtils

/**
 * Created by yeqf on 2018/3/10.
 */
class CategoryAdapter(layoutResId: Int, data: List<GankIoCache>) : BaseQuickAdapter<GankIoCache, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: GankIoCache) {
        helper?.setText(R.id.title, item.desc)
        helper?.setText(R.id.time, TimeUtils.getTime(item.publishedAt, TimeUtils.FORMAT_YYYYMMDD_SLASH))
        val author = item.who
        val viaTv = helper?.getView<TextView>(R.id.author)
        if(author != null) {
            viaTv?.text = "via. $author"
        } else {
            viaTv?.visibility = View.GONE
        }
        val source = SitesHelper.getSiteName(item.url)
        val srcTv = helper?.getView<TextView>(R.id.source)
        srcTv?.text = source
        val backgroundColor = when (source) {
            "GitHub" -> R.color.md_black
            "B站" -> R.color.pink_bilibili
            "微信分享" -> R.color.md_green_200
            "简书" -> R.color.orange_jianshu
            "微博" -> R.color.red_weibo
            "秒拍" -> R.color.yellow_miaopai
            "知乎" -> R.color.blue_zhihu
            else -> R.color.md_deep_purple_400
        }
        srcTv?.setBackground(ShapeUtils.getShapeDrawable(mContext, 4f, backgroundColor))
    }

}