package com.app2641.moonlovers.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.app2641.moonlovers.utils.DateUtils

class MoonLoversPreference(context: Context) {
    companion object {
        const val LAST_FETCHED_AT_KEY = "last_fetched_at"
        const val MOON_AGE_KEY = "moon_age"
        const val INSTALLED_AT = "initial_date_at"
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

    fun getInstalledAt(): String {
        var installedAt = preference.getString(INSTALLED_AT, null)

        if (installedAt == null) {
            installedAt = DateUtils.toString(DateUtils.now())

            editor.putString(INSTALLED_AT, installedAt)
            editor.apply()
        }

        return installedAt.toString()
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
