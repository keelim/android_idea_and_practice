package com.keelim.practice11

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class Ut1 : Fragment() {
    var webView1: WebView? = null
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                webViewGoBack()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.ut1, container, false)
        val url = "http://www.seoul.go.kr/v2012/find.html"
        webView1 = rootView.findViewById(R.id.webview1)
        webView1.getSettings().javaScriptEnabled = true
        webView1.getSettings().setUserAgentString("Android")
        webView1.setInitialScale(70)
        webView1.getSettings().builtInZoomControls = true
        webView1.getSettings().displayZoomControls = false
        webView1.setWebViewClient(WebViewClient()) // 이걸 안해주면 새창이 뜸
        webView1.loadUrl(url)
        webView1.setOnKeyListener(View.OnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && webView1.canGoBack()) {
                handler.sendEmptyMessage(1)
                return@setOnKeyListener true
            }
            false
        })
        return rootView
    }

    private fun webViewGoBack() {
        webView1!!.goBack()
    }
}