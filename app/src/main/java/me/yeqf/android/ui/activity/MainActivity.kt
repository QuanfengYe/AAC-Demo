package me.yeqf.android.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import me.yeqf.android.App
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

//    override fun onResume() {
//        super.onResume()
//        supportActionBar?.title = "Gank.io"
//        // Create a Constraints that defines when the task should run
//        val myConstraints = Constraints.Builder()
//                .setRequiresDeviceIdle(true)//设备闲时
//                .setRequiresCharging(true)//充电时
//                // Many other constraints are available, see the
//                // Constraints.Builder reference
//                .build()
//        val request = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
//                .setConstraints(myConstraints)
//                .setInputData(Data.Builder().putString("key", "value").build())
//                .build()
//        WorkManager.getInstance().enqueue(request)
//        WorkManager.getInstance().getStatusById(request.id).observe(this, Observer {
//            /**doSomething*/
//            val result = it?.outputData
//            if(it?.state?.isFinished == true)
//                Toast.makeText(this@MainActivity, "work finish!", Toast.LENGTH_SHORT).show()
//            else
//                Toast.makeText(this@MainActivity, "work fained!", Toast.LENGTH_SHORT).show()
//        })
//    }
//
//    class DownloadWorker: Worker() {
//        override fun doWork(): WorkerResult {
//            // Fetch the arguments (and specify default values):
//            val value = inputData.getString("key", "default")
//            //doWork
//            Thread.sleep(1000)
//            //...set the output, and we're done!
//            outputData = Data.Builder().putString("out", "value").build()
//            // Indicate success or failure with your return value.
//            return WorkerResult.SUCCESS
//        }
//    }

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

    override fun onDestroy() {
        super.onDestroy()

        App.mApp.getRefWatcher().watch(this)
    }
}
