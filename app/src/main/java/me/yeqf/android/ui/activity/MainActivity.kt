package me.yeqf.android.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import me.yeqf.android.R
import me.yeqf.android.base.BaseActivity
import me.yeqf.android.ui.adapter.viewpager.MainFragmentAdapter

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = arrayListOf("最新", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App")
        viewPager.addOnPageChangeListener(onPageChangeListener)
        viewPager.adapter = MainFragmentAdapter(supportFragmentManager, data)
        mTabBar.setData(data)
        mTabBar.setupWithViewPager(viewPager)
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.title = "Gank.io"
    }

    override fun getContentRes(): Int = R.layout.activity_main

    override fun getMenuRes(): Int = R.menu.menu_main

    override fun getToolBar(): Toolbar = toolbar

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            if(state != ViewPager.SCROLL_STATE_IDLE)
                appBarLayout.setExpanded(true, true)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {

        }
    }
}
