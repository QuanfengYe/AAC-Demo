package me.yeqf.android.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_category.*
import me.yeqf.android.R
import me.yeqf.android.ui.adapter.viewpager.CategoryFragmentAdapter

/**
 * Created by yeqf on 2018/3/10.
 */
class CategoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val data = arrayListOf("Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App")
        mTabBar.setData(data)
        viewPager.adapter = CategoryFragmentAdapter(childFragmentManager, data)
        mTabBar.setupWithViewPager(viewPager)
    }
}