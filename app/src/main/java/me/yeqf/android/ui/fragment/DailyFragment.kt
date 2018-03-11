package me.yeqf.android.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_daily.*
import me.yeqf.android.R
import me.yeqf.android.library.brvah.bean.GankIoSection
import me.yeqf.android.persistence.entity.GankIoCache
import me.yeqf.android.ui.activity.WebActivity
import me.yeqf.android.ui.adapter.recyclerview.DailyAdapter
import me.yeqf.android.ui.viewmodel.DailyViewModel
import me.yeqf.android.utils.DataUtils
import me.yeqf.common.utils.TimeUtils

/**
 * Created by yeqf on 2018/3/10.
 */
class DailyFragment : Fragment() {

    private lateinit var mViewModel: DailyViewModel
    private lateinit var mLayoutManager: LinearLayoutManager
    private var mAdapter: DailyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        lifecycle.addObserver(mViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mViewModel.getDate {
            requestDailyData(it)
            date.text = TimeUtils.transform(it, TimeUtils.FORMAT_YYYY_MM_DD, TimeUtils.FORMAT_YYYYMMDD_SLASH)
        }
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
        recyclerView.layoutManager = mLayoutManager
        mAdapter = DailyAdapter(R.layout.item_recyclerview_daily, R.layout.section_recyclerview_daily, data)
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val obj = data[position]
            if (!obj.isHeader)
                WebActivity.open(activity!!, data[position].t.url, data[position].t.desc)
        }
        recyclerView.adapter = mAdapter
    }
}