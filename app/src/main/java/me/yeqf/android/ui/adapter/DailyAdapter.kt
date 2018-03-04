package me.yeqf.android.ui.adapter

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.yeqf.android.R
import me.yeqf.android.library.brvah.bean.GankIoSection

/**
 * Created by yeqf on 2018/3/3.
 */
class DailyAdapter(layoutResId: Int, sectionHeadResId: Int, data: List<GankIoSection>) :
        BaseSectionQuickAdapter<GankIoSection, BaseViewHolder>(layoutResId, sectionHeadResId, data) {

    override fun convertHead(helper: BaseViewHolder?, item: GankIoSection?) {
        helper?.setText(R.id.name, item?.header)
    }

    override fun convert(helper: BaseViewHolder?, item: GankIoSection?) {
        helper?.setText(R.id.title, item?.t?.desc)
    }
}