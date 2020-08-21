package com.keelim.practice11

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class Ut2 : Fragment() {
    var webView2: WebView? = null
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            if (message.what == 1) {
                webViewGoBack()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.ut2, container, false)
        val url = "https://www.lost112.go.kr/"
        webView2 = rootView.findViewById(R.id.webview2)
        webView2.getSettings().javaScriptEnabled = true
        webView2.getSettings().setUserAgentString("Android")
        webView2.setInitialScale(70)
        webView2.getSettings().builtInZoomControls = true
        webView2.getSettings().displayZoomControls = false
        webView2.setWebViewClient(WebViewClient()) // 이걸 안해주면 새창이 뜸
        webView2.loadUrl(url)
        webView2.setOnKeyListener(View.OnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && webView2.canGoBack()) {
                handler.sendEmptyMessage(1)
                return@setOnKeyListener true
            }
            false
        })
        return rootView
    }

    private fun webViewGoBack() {
        webView2!!.goBack()
    }
}