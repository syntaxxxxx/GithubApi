package com.langitpayment.githubapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.langitpayment.githubapi.entity.TrendingRepositories
import com.langitpayment.githubapi.network.Injection
import com.langitpayment.seller.utils.state.LoaderState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    private val _result = MutableLiveData<List<TrendingRepositories>>()
    val result: LiveData<List<TrendingRepositories>>
        get() = _result

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        getRepositories()
    }

    fun getRepositories() {
        viewModelScope.launch {
            _state.value = LoaderState.ShowLoading
            try {
                _result.value = Injection.provideGithubApiService().getRepositories()
                _state.value = LoaderState.HideLoading
            } catch (e: Throwable) {
                _error.value = e.printStackTrace().toString()
            }
        }
    }
}