package me.yeqf.android.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import me.yeqf.android.R
import me.yeqf.android.base.BaseActivity
import me.yeqf.android.library.brvah.bean.GankIoSection
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.ui.adapter.DailyAdapter
import me.yeqf.android.ui.viewmodel.DailyViewModel
import me.yeqf.android.utils.DataUtils
import me.yeqf.common.utils.TimeUtils

class MainActivity : BaseActivity() {

    private lateinit var mViewModel: DailyViewModel
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: DailyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        lifecycle.addObserver(mViewModel)

        initView()
    }

    override fun onStart() {
        super.onStart()
        mViewModel.getDate {
            requestDailyData(it)
            date.text = TimeUtils.transform(it, TimeUtils.FORMAT_YYYY_MM_DD, TimeUtils.FORMAT_YYYYMMDD_SLASH)
        }
    }

    override fun onResume() {
        super.onResume()

        supportActionBar?.title = "Gank.io"
    }

    private fun initView() {
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
    }

    private fun requestDailyData(time: String) {
        val date = TimeUtils.getDateArray(time, TimeUtils.FORMAT_YYYY_MM_DD)
        mViewModel.getDaily(date) {
            for (obj: GankIoCache in it) {
                if (obj.type == "福利") {
                    loadDailyImage(obj.url)
                    break
                }
            }
            notifyRecyclerView(DataUtils.getSectionData(it))
        }
    }

    private fun loadDailyImage(url: String?) {
        Glide.with(this).load(url).into(poster)
    }

    private fun notifyRecyclerView(data: List<GankIoSection>) {
        mAdapter = DailyAdapter(R.layout.item_recyclerview_daily, R.layout.section_recyclerview_daily, data)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val obj = data[position]
            if(!obj.isHeader)
                WebActivity.open(this, data[position].t.url, data[position].t.desc)
        }
        recyclerView.adapter = mAdapter
    }

    override fun getContentRes(): Int = R.layout.activity_main

    override fun getMenuRes(): Int = R.menu.menu_main

    override fun getToolBar(): Toolbar = toolbar
}
