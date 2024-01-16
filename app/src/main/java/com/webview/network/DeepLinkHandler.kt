package com.webview.network

import android.content.Context
import android.net.Uri

class DeepLinkHandler(private val context: Context) {
    fun handleDeeplink(deepLink: String?) {
        deepLink?.let {
            //processing logic
            val uri = Uri.parse(it)
            // diplink params processing
            val param1 = uri.getQueryParameter("param1")
            val param2 = uri.getQueryParameter("param2")
        }
    }
}