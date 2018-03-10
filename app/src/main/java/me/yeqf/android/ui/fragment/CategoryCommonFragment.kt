package me.yeqf.android.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_category_common.*
import me.yeqf.android.R
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.ui.activity.WebActivity
import me.yeqf.android.ui.adapter.recyclerview.CategoryAdapter
import me.yeqf.android.ui.viewmodel.CategoryViewModel

/**
 * Created by yeqf on 2018/3/10.
 */
class CategoryCommonFragment : Fragment() {

    private lateinit var mViewModel: CategoryViewModel
    private var mAdapter: CategoryAdapter? = null
    private lateinit var mLayoutManager: LinearLayoutManager
    private var mCategory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        lifecycle.addObserver(mViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_common, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mCategory = arguments?.get("category") as String
        mViewModel.getCategory(mCategory, 20, 1) {
            notifyRecyclerView(it)
            Log.d("Category", it.toString())
        }
    }

    private fun notifyRecyclerView(data: List<GankIoCache>) {
        recyclerView.layoutManager = mLayoutManager
        mAdapter = CategoryAdapter(R.layout.item_recyclerview_daily, data)
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val obj = data[position]
            WebActivity.open(activity!!, obj.url, obj.desc)
        }
        recyclerView.adapter = mAdapter
    }
}
