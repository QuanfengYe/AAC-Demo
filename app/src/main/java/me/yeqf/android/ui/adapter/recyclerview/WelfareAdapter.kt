package me.yeqf.android.ui.adapter.recyclerview

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.yeqf.android.R
import me.yeqf.android.persistence.entity.GankIoCache

/**
 * Created by yeqf on 2018/3/11.
 */
class WelfareAdapter(layoutResId: Int, data: List<GankIoCache>) : BaseQuickAdapter<GankIoCache, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: GankIoCache?) {
        Glide.with(mContext).load(item?.url).into(helper?.getView(R.id.imageView) as ImageView)
    }

}