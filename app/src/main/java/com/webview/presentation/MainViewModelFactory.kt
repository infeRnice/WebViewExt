package com.webview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.webview.network.NetworkRepository

class MainViewModelFactory(private val networkRepository: NetworkRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
        return MainViewModel(networkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}