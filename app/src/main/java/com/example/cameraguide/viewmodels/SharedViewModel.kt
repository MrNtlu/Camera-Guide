package com.example.cameraguide.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val _isPermissionGranted = MutableLiveData(false)
    val isPermissionGranted: LiveData<Boolean> = _isPermissionGranted

    fun setPermission(isGranted: Boolean) {
        _isPermissionGranted.value = isGranted
    }
}