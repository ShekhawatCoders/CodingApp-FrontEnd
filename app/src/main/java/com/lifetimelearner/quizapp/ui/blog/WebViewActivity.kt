package com.lifetimelearner.quizapp.ui.blog

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.lifetimelearner.quizapp.R
import im.delight.android.webview.AdvancedWebView


class WebViewActivity : AppCompatActivity(), AdvancedWebView.Listener {

    private val LOG_TAG = "LOGGING"
    private lateinit var mWebView: AdvancedWebView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // hideStatusBar();
        var url = intent.getStringExtra("BLOG_URL") ?: "https://coding-interview-questions.blogspot.com/"

        shimmerFrameLayout = findViewById(R.id.shimmer_webview)
        mWebView = findViewById(R.id.webview)
        mWebView.setListener(this, this)
        mWebView.setMixedContentAllowed(false)
        mWebView.loadUrl(url)

    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            actionBar?.hide()
        }
    }

    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        mWebView.onResume()
        // ...
    }

    @SuppressLint("NewApi")
    override fun onPause() {
        mWebView.onPause()
        // ...
        super.onPause()
    }

    override fun onDestroy() {
        mWebView.onDestroy()
        // ...
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        mWebView.onActivityResult(requestCode, resultCode, intent)
        // ...
    }

    override fun onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return
        }
        // ...
        super.onBackPressed()
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPageFinished(url: String?) {
        shimmerFrameLayout.stopShimmerAnimation()
        shimmerFrameLayout.visibility = View.GONE
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
        mWebView.visibility = View.GONE
        shimmerFrameLayout.startShimmerAnimation()
        shimmerFrameLayout.visibility = View.VISIBLE
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun onExternalPageRequest(url: String?) {
        TODO("Not yet implemented")
    }
}