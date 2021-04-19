package com.app2641.moonlovers.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class MoonLoversPreference(context: Context) {
    private val lastFetchedAtKey = "last_fetched_at"
    private val moonAgeKey = "moon_age"
    private val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor by lazy {
        preference.edit()
    }

    fun getLastFetchedAt(): String {
        return preference.getString(lastFetchedAtKey, "2021/01/01 00:00").toString()
    }

    fun getMoonAge(): String {
        return preference.getString(moonAgeKey, "15.0").toString()
    }

    fun putLastFetchedAt(lastFetchedAt: String): MoonLoversPreference {
        editor.putString(lastFetchedAtKey, lastFetchedAt)
        return this
    }

    fun putMoonAge(moonAge: String): MoonLoversPreference {
        editor.putString(moonAgeKey, moonAge)
        return this
    }

    fun apply() {
        editor.apply()
    }
}