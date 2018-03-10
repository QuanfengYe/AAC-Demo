package me.yeqf.android.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import me.yeqf.android.R
import me.yeqf.android.base.BaseActivity
import me.yeqf.android.ui.adapter.viewpager.MainFragmentAdapter

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = MainFragmentAdapter(supportFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.title = "Gank.io"
    }

    override fun getContentRes(): Int = R.layout.activity_main

    override fun getMenuRes(): Int = R.menu.menu_main

    override fun getToolBar(): Toolbar = toolbar
}
