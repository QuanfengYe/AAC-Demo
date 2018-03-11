package me.yeqf.android.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_category_common.*
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.ui.activity.WebActivity
import me.yeqf.android.ui.viewmodel.CategoryViewModel

/**
 * Created by yeqf on 2018/3/11.
 */
open abstract class BaseCategoryFragment : Fragment() {

    private lateinit var mViewModel: CategoryViewModel
    private var mAdapter: BaseQuickAdapter<GankIoCache, BaseViewHolder>? = null
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var mCategory = ""
    private val mPageNum = 20
    private var mPageIdx = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        lifecycle.addObserver(mViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        mPageIdx = 1
        mLayoutManager = getLayoutManager()
        mCategory = arguments?.get("category") as String
        requestCategoryData(false)
    }

    private fun requestCategoryData(loadMore: Boolean) {
        mViewModel.getCategory(mCategory, mPageNum, mPageIdx) {
            notifyRecyclerView(it, loadMore)
        }
    }

    private fun notifyRecyclerView(data: List<GankIoCache>, loadMore: Boolean) {
        /**由于room特性，当数据库更新时会主动发起查询操作
         * 如加载更多操作，当加载第5页时，数据库缓存中没有，从网络上加载再保存到数据库中
         * 这时，会触发分别加载第1-5页的五次操作，需屏蔽前四次
         * */
        if(data.isNotEmpty() && data[0].page < mPageIdx) {
            return
        }
        if (loadMore) {
            if (data.isNotEmpty())
                mAdapter?.addData(data)
            if (data.size < mPageNum) {
                mAdapter?.loadMoreEnd()
            } else {
                mAdapter?.loadMoreComplete()
            }
        } else {
            recyclerView.layoutManager = mLayoutManager
            mAdapter = getAdapter(data)
            mAdapter?.setOnItemClickListener { _, _, position ->
                val obj = data[position]
                WebActivity.open(activity!!, obj.url, obj.desc)
            }
            mAdapter?.setOnLoadMoreListener({
                recyclerView.postDelayed({
                    mPageIdx++
                    requestCategoryData(true)
                }, 100)
            }, recyclerView)
            recyclerView.adapter = mAdapter
        }
    }

    abstract fun getLayoutResId(): Int

    abstract fun getLayoutManager(): RecyclerView.LayoutManager

    abstract fun getAdapter(data: List<GankIoCache>): BaseQuickAdapter<GankIoCache, BaseViewHolder>
}