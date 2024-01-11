package com.webview.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webview.network.NetworkRepository
import kotlinx.coroutines.launch

class MainViewModel(private val networkRepository: NetworkRepository) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun checkModerationStatus() {
        viewModelScope.launch {
            val result = networkRepository.fetchNetWorkData()
            _viewState.value = if (result) ViewState.ShowWebView else ViewState.ShowPlaceholder
        }
    }
    sealed class ViewState {
        object ShowWebView : ViewState()
        object ShowPlaceholder : ViewState()
    }
}