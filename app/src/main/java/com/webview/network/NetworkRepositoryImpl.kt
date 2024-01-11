package com.webview.network

import android.os.Build

class NetworkRepositoryImpl(private val apiService: ApiService) : NetworkRepository{

        override suspend fun fetchNetWorkData(): Boolean {
            val response = apiService.fetchData() // Получение данных от API
            val isGoogleDevice = Build.MANUFACTURER.equals("Google", ignoreCase = true)
            //filters logic
            return response.networkName != "google" &&
                    response.country in listOf("AllowedCountry1", "AllowedCountry2") &&
                    response.hasSimCard && !isGoogleDevice
        }
}