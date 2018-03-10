package me.yeqf.android.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.*
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.common_toolbar.*
import me.yeqf.android.R
import me.yeqf.android.base.BaseActivity
import me.yeqf.android.base.BaseSwipeRefreshActivity
import me.yeqf.android.view.ISwipeRefresh

/**
 * Created by yeqf on 2018/3/4.
 */
class WebActivity : BaseSwipeRefreshActivity() {

    companion object {
        private const val EXTRA_URL = "URL"
        private const val EXTRA_TITLE = "TITLE"

        fun open(ctx: Context, url: String?, title: String?) {
            val intent = Intent(ctx, WebActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            intent.putExtra(EXTRA_TITLE, title)
            ctx.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra(EXTRA_URL)
        val title = intent.getStringExtra(EXTRA_TITLE)
        setTitle(title)
        setBackButtonEnable(true)

        web.settings.javaScriptEnabled = true
        web.settings.loadWithOverviewMode = true
        web.settings.setAppCacheEnabled(true)
        web.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        web.settings.setSupportZoom(true)
        web.webViewClient = WebClient(this)
        web.loadUrl(url)
    }

    override fun onRefresh() {
        web.reload()
    }

    override fun onResume() {
        super.onResume()
        web.onResume()
    }

    override fun onPause() {
        super.onPause()
        web.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        web.destroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    class WebClient(private val swipe: ISwipeRefresh): WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (TextUtils.isEmpty(request?.url.toString())) {
                return true
            }
            if (request?.url?.host == "gank.io") {
                return false
            }
            view?.loadUrl(request?.url?.toString())
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            swipe.showRefresh()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            swipe.hideRefresh()
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
        }
    }

    override fun getContentRes(): Int = R.layout.activity_web

    override fun getMenuRes(): Int = R.menu.menu_web

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefresh

    override fun getToolBar(): Toolbar = toolbar
}