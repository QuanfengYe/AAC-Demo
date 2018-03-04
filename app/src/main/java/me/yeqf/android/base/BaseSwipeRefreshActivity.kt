package me.yeqf.android.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import me.yeqf.android.R
import me.yeqf.android.view.ISwipeRefresh

/**
 * Created by yeqf on 2018/3/4.
 */
abstract class BaseSwipeRefreshActivity : BaseActivity(), ISwipeRefresh {

    protected lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeRefreshLayout = getSwipeRefreshLayout()
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        mSwipeRefreshLayout.setOnRefreshListener { onRefresh() }
    }

    abstract fun getSwipeRefreshLayout(): SwipeRefreshLayout

    abstract fun onRefresh()

    override fun showRefresh() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun hideRefresh() {
        mSwipeRefreshLayout.isRefreshing = false
    }
}