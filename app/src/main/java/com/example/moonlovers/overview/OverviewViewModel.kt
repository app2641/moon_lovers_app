package com.example.moonlovers.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moonlovers.network.MoonAgeApi
import com.example.moonlovers.network.MoonAgeProperty
import kotlinx.coroutines.launch

private const val TAG = "API"

class OverviewViewModel : ViewModel() {
    private val _age = MutableLiveData<Double>()

    val age: LiveData<Double>
        get() = _age

    init {
        getMoonAgeProperties()
    }

    fun getMoonAgeProperties() {
        viewModelScope.launch {
            try {
                val moonAge: MoonAgeProperty = MoonAgeApi.retrofitService.getMoonAge()
                _age.value = moonAge.age
                Log.e(TAG, "success!!!")
            } catch (e: Exception) {
                Log.e(TAG, "test", e)
            }
        }
    }
}