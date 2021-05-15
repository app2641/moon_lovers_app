package com.app2641.moonlovers.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.app2641.moonlovers.utils.DateUtils

class MoonLoversPreference(context: Context) {
    companion object {
        const val LAST_FETCHED_AT_KEY = "last_fetched_at"
        const val MOON_AGE_KEY = "moon_age"
        const val REVIEWED_AT = "last_reviewed_at"
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

    fun getReviewedAt(): String {
        var reviewedAt = preference.getString(REVIEWED_AT, null)

        if (reviewedAt == null) {
            reviewedAt = DateUtils.toString(DateUtils.now())

            editor.putString(REVIEWED_AT, reviewedAt)
            editor.apply()
        }

        return reviewedAt.toString()
    }

    fun putLastFetchedAt(lastFetchedAt: String): MoonLoversPreference {
        editor.putString(LAST_FETCHED_AT_KEY, lastFetchedAt)
        return this
    }

    fun putMoonAge(moonAge: String): MoonLoversPreference {
        editor.putString(MOON_AGE_KEY, moonAge)
        return this
    }

    fun putReviewedAt(reviewedAt: String): MoonLoversPreference {
        editor.putString(REVIEWED_AT, reviewedAt)
        return this
    }

    fun apply() {
        editor.apply()
    }
}
