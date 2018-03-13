package me.yeqf.android.ui.fragment

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yeqf.expandrecyclerview.Dividers.LinearDecoration
import me.yeqf.android.R
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.ui.adapter.recyclerview.CategoryAdapter
import me.yeqf.common.utils.Tools

/**
 * Created by yeqf on 2018/3/10.
 */
class CommonCategoryFragment : BaseCategoryFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_category_common
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return LinearDecoration(RecyclerView.VERTICAL, Tools.dp2px(activity!!, 5f), ContextCompat.getColor(activity!!, R.color.md_grey_200))
    }

    override fun getAdapter(data: List<GankIoCache>): BaseQuickAdapter<GankIoCache, BaseViewHolder> {
        return CategoryAdapter(R.layout.item_recyclerview_category, data)
    }
}
