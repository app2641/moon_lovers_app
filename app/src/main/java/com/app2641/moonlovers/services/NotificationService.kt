package com.app2641.moonlovers.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.app2641.moonlovers.R
import com.google.firebase.messaging.FirebaseMessaging

class NotificationService(applicationContext: Context) {
    private val context = applicationContext

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                context.getString(R.string.fcm_channel_id),
                context.getString(R.string.fcm_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setShowBadge(false)
                description = context.getString(R.string.fcm_channel_description)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(context.getString(R.string.fcm_tonight_topic))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ML", "success subscribe topic!")
                } else {
                    Log.d("ML", "failure subscribe topic!")
                }
            }
    }
}