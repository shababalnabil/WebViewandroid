package com.nabil.webviewandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webview : WebView = findViewById(R.id.webview)
        webview.settings.javaScriptEnabled = true
        webview.apply {
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
        }

        webview.loadUrl(InfoClass.url)

    }
}