package me.yeqf.android.ui.adapter.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.yeqf.android.ui.fragment.CategoryCommonFragment

/**
 * Created by yeqf on 2018/3/10.
 */
class CategoryFragmentAdapter(fm: FragmentManager, private val titles: List<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment = CategoryCommonFragment()
        val bundle = Bundle()
        bundle.putString("category", titles[position])
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return titles.size
    }
}
