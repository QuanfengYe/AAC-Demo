package me.yeqf.android.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu

/**
 * Created by yeqf on 2018/3/4.
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentRes())
        initToolbar()
    }

    private fun initToolbar() {
        mToolbar = getToolBar()
        setSupportActionBar(mToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(getMenuRes() >= 0)
            menuInflater.inflate(getMenuRes(), menu)
        return true
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setBackButtonEnable(show: Boolean) {
        supportActionBar?.setHomeButtonEnabled(show)
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
        mToolbar.setNavigationOnClickListener { finish() }
    }

    abstract fun getContentRes(): Int

    abstract fun getMenuRes(): Int

    abstract fun getToolBar(): Toolbar
}