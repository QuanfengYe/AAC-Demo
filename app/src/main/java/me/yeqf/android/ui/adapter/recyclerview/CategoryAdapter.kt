package me.yeqf.android.ui.adapter.recyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.yeqf.android.R
import me.yeqf.android.persistence.entity.GankIoCache

/**
 * Created by yeqf on 2018/3/10.
 */
class CategoryAdapter(layoutResId: Int, data: List<GankIoCache>) : BaseQuickAdapter<GankIoCache, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: GankIoCache?) {
        helper?.setText(R.id.title, item?.desc)
    }

}