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
import com.app2641.moonlovers.utils.DateUtils
import kotlinx.coroutines.launch

enum class MoonLoversApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val moonLoverPref: MoonLoversPreference by lazy {
        MoonLoversPreference(application)
    }

    private val lastFetchedAt = moonLoverPref.getLastFetchedAt()

    private val _age = MutableLiveData<String>()

    private val _status = MutableLiveData<MoonLoversApiStatus>()

    val age: LiveData<String>
        get() = _age

    val status: LiveData<MoonLoversApiStatus>
        get() = _status

    fun getMoonAgeProperties() {
        setApiStatus(MoonLoversApiStatus.LOADING)

        when {
            twoHoursHavePassed() -> {
                fetchMoonAge()
            }
            overTwentyOclock() -> {
                fetchMoonAge()
            }
            else -> {
                _age.value = moonLoverPref.getMoonAge()
                setApiStatus(MoonLoversApiStatus.DONE)
            }
        }
    }

    // 前回の取得から二時間が経過しているかどうか
    private fun twoHoursHavePassed(): Boolean {
        return try {
            val fetchedDateTime = DateUtils.toZoneDateTime(lastFetchedAt)
            val minutes = DateUtils.dateDiff(fetchedDateTime, DateUtils.now())

            120 < minutes
        } catch(e: Exception) {
            false
        }
    }

    // 22時を過ぎていて、かつlastFetchedAtが22時前かどうか
    private fun overTwentyOclock(): Boolean {
        // 現在日時が22時を過ぎているかどうか
        val twentyOclock = DateUtils.now().withHour(22).withMinute(0).withSecond(0)
        val ret = DateUtils.dateDiff(DateUtils.now(), twentyOclock)

        // 22時前だと正の値になる
        if (ret <= 0) {
            return false
        }

        // 最終取得日時が22時前かどうか
        val fetchedDateTime = DateUtils.toZoneDateTime(lastFetchedAt)
        val ret2 = DateUtils.dateDiff(fetchedDateTime, twentyOclock)

        // 22時前だと正の値になる
        return 0 < ret2
    }

    private fun fetchMoonAge() {
        viewModelScope.launch {
            try {
                val moonAge: MoonAgeProperty = MoonAgeApi.retrofitService.getMoonAge()
                _age.value = moonAge.age.toString()
                setApiStatus(MoonLoversApiStatus.DONE)
                Log.e("MoonLover", "age: " + moonAge.age.toString())
                updatePref()
            } catch (e: Exception) {
                Log.e("MoonLover", "Log", e)
                setApiStatus(MoonLoversApiStatus.ERROR)
            }
        }
    }

    private fun updatePref() {
        val lastFetchedAt = DateUtils.toString(DateUtils.now())

        moonLoverPref.putMoonAge(_age.value.toString())
            .putLastFetchedAt(lastFetchedAt)
            .apply()
    }

    private fun setApiStatus(status: MoonLoversApiStatus) {
        _status.value = status
    }
}