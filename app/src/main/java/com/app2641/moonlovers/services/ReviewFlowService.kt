package com.app2641.moonlovers.services

import android.util.Log
import com.app2641.moonlovers.MainActivity
import com.app2641.moonlovers.preferences.MoonLoversPreference
import com.app2641.moonlovers.utils.DateUtils
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.testing.FakeReviewManager
import com.google.android.play.core.tasks.Task

class ReviewFlowService(mainActivity: MainActivity) {
    private val activity = mainActivity
    private val context = activity.applicationContext

    fun start() {
        if (!isStart()) {
            return
        }

        val manager = FakeReviewManager(context)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo> ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener {  }
            } else {
                Log.e("ML dev", task.exception?.message.toString())
            }
        }
        request.addOnFailureListener { e ->
            Log.e("ML dev", e.message.toString())
        }
    }

    private

    // インストールから一ヶ月後かどうか
    fun isStart(): Boolean {
        val pref = MoonLoversPreference(context)
        val installedAt = DateUtils.toZoneDateTime(pref.getInstalledAt())
        val minutes = DateUtils.dateDiff(installedAt, DateUtils.now())

        return 43200 < minutes
    }
}