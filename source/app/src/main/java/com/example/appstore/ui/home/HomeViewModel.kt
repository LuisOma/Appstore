package com.example.appstore.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appstore.base.BaseViewModel
import com.example.appstore.data.domain.core.ResponseState
import com.example.appstore.data.dataSource.AppsDataSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    var repository = AppsDataSource()
    val getAppsResponse = MutableLiveData<ResponseState<*>>()

    fun getAppsList() = viewModelScope.launch {

        try {
            repository.getApps().collectLatest {
                getAppsResponse.postValue(it)
            }
        } catch (securityException: SecurityException) {
            throw securityException
        }
    }
}