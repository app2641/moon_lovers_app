package com.example.moonlovers.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moonlovers.network.MoonAgeApi
import com.example.moonlovers.network.MoonAgeProperty
import com.example.moonlovers.preferences.MoonLoversPreference
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val moonLoverPref: MoonLoversPreference by lazy {
        MoonLoversPreference(application)
    }

    private val lastFetchedAt = moonLoverPref.getLastFetchedAt()

    private val _age = MutableLiveData<String>()

    val age: LiveData<String>
        get() = _age

    fun getMoonAgeProperties() {
        if (twoHoursHavePassed()) {
            viewModelScope.launch {
                try {
                    val moonAge: MoonAgeProperty = MoonAgeApi.retrofitService.getMoonAge()
                    _age.value = moonAge.age.toString()
                } catch (e: Exception) {
                    _age.value = lastFetchedAt
                }
            }
        }
    }

    private fun twoHoursHavePassed(): Boolean {
        return try {
            val formatter =  DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm")
            val fetchedDateTime = LocalDateTime.parse(lastFetchedAt, formatter)
            val minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), fetchedDateTime)

            120 < minutes
        } catch(e: Exception) {
            false
        }
    }
}