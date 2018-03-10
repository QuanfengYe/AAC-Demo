package me.yeqf.android.ui.adapter.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.yeqf.android.ui.fragment.CategoryFragment
import me.yeqf.android.ui.fragment.DailyFragment

/**
 * Created by yeqf on 2018/3/10.
 */
class MainFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val titles = arrayOf("Daily", "Category")

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return DailyFragment()
            else -> return CategoryFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }
}