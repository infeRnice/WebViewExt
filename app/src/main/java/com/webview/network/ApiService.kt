package com.webview.network

import retrofit2.http.GET

interface ApiService {
    @GET("path")
    suspend fun fetchData(): ApiResponse
}