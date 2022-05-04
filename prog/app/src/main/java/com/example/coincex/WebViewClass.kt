package com.example.coincex

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.InputStream

class WebViewClass(): WebViewClient() {
    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        view?.loadUrl(url)
        return true
    }
}
