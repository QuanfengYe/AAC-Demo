package me.yeqf.android.ui.fragment

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yeqf.expandrecyclerview.Dividers.GridDecoration
import me.yeqf.android.R
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.ui.adapter.recyclerview.WelfareAdapter
import me.yeqf.common.utils.Tools

/**
 * Created by yeqf on 2018/3/11.
 */
class WelfareCategoryFragment : BaseCategoryFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_category_common
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return GridDecoration(Tools.dp2px(activity!!, 8f), ContextCompat.getColor(activity!!, R.color.md_grey_200), true)
    }

    override fun getAdapter(data: List<GankIoCache>): BaseQuickAdapter<GankIoCache, BaseViewHolder> {
        return WelfareAdapter(R.layout.item_recyclerview_welfare, data)
    }
}