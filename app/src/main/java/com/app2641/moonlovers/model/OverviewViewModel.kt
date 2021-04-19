package com.app2641.moonlovers.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app2641.moonlovers.network.MoonAgeApi
import com.app2641.moonlovers.network.MoonAgeProperty
import com.app2641.moonlovers.preferences.MoonLoversPreference
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

enum class MoonLoversApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val moonLoverPref: MoonLoversPreference by lazy {
        MoonLoversPreference(application)
    }

    private val lastFetchedAt = moonLoverPref.getLastFetchedAt()

    private val formatter =  DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm")

    private val _age = MutableLiveData<String>()

    private val _status = MutableLiveData<MoonLoversApiStatus>()

    val age: LiveData<String>
        get() = _age

    val status: LiveData<MoonLoversApiStatus>
        get() = _status

    fun getMoonAgeProperties() {
        setApiStatus(MoonLoversApiStatus.LOADING)

        if (twoHoursHavePassed()) {
            viewModelScope.launch {
                try {
                    val moonAge: MoonAgeProperty = MoonAgeApi.retrofitService.getMoonAge()
                    _age.value = moonAge.age.toString()
                    setApiStatus(MoonLoversApiStatus.DONE)
                    updatePref()
                } catch (e: Exception) {
                    Log.e("MoonLover", "Log", e)
                    setApiStatus(MoonLoversApiStatus.ERROR)
                }
            }
        } else {
            _age.value = moonLoverPref.getMoonAge()
           setApiStatus(MoonLoversApiStatus.DONE)
        }
    }

    private fun twoHoursHavePassed(): Boolean {
        return try {
            val fetchedDateTime = LocalDateTime.parse(lastFetchedAt, formatter)
            val minutes = ChronoUnit.MINUTES.between(fetchedDateTime, LocalDateTime.now())

            120 < minutes
        } catch(e: Exception) {
            false
        }
    }

    private fun updatePref() {
        val current = LocalDateTime.now()
        val lastFetchedAt = current.format(formatter)

        moonLoverPref.putMoonAge(_age.value.toString())
            .putLastFetchedAt(lastFetchedAt)
            .apply()
    }

    private fun setApiStatus(status: MoonLoversApiStatus) {
        _status.value = status
    }
}