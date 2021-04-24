package com.app2641.moonlovers.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class MoonLoversPreference(context: Context) {
    companion object {
        val LAST_FETCHED_AT_KEY = "last_fetched_at"
        val MOON_AGE_KEY = "moon_age"
    }

    private val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor by lazy {
        preference.edit()
    }
    private val defaultLastFetchedAt = "2021-01-01T00:00:00+09:00[Asia/Tokyo]"

    fun getLastFetchedAt(): String {
        return preference.getString(LAST_FETCHED_AT_KEY, defaultLastFetchedAt).toString()
    }

    fun getMoonAge(): String {
        return preference.getString(MOON_AGE_KEY, "15.0").toString()
    }

    fun putLastFetchedAt(lastFetchedAt: String): MoonLoversPreference {
        editor.putString(LAST_FETCHED_AT_KEY, lastFetchedAt)
        return this
    }

    fun putMoonAge(moonAge: String): MoonLoversPreference {
        editor.putString(MOON_AGE_KEY, moonAge)
        return this
    }

    fun apply() {
        editor.apply()
    }
}
