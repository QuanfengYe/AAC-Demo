package me.yeqf.android.ui.adapter.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import me.yeqf.android.ui.fragment.CommonCategoryFragment
import me.yeqf.android.ui.fragment.DailyFragment
import me.yeqf.android.ui.fragment.WelfareCategoryFragment

/**
 * Created by yeqf on 2018/3/10.
 */
class MainFragmentAdapter(fm: FragmentManager, private val titles: List<String>) : FragmentPagerAdapter(fm) {

    private val mFragments = SparseArray<Fragment>()

    override fun getItem(position: Int): Fragment {
        var fragment = mFragments[position]
        if(fragment == null) {
            fragment = when (position) {
                0 -> DailyFragment()
                4 -> WelfareCategoryFragment()
                else -> CommonCategoryFragment()
            }
            mFragments.put(position, fragment)
        }

        fragment.arguments = Bundle().apply { putString("category", titles[position]) }
        return fragment
    }

    override fun getCount(): Int {
        return titles.size
    }
}