package com.webview.network

interface NetworkRepository{

    suspend fun fetchNetWorkData(): Boolean

}