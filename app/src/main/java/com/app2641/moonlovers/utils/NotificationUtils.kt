package com.app2641.moonlovers.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.app2641.moonlovers.MainActivity
import com.app2641.moonlovers.R

class NotificationUtils {
    companion object {
        fun sendNotification(messageBody: String, context: Context) {
            val contentIntent = Intent(context, MainActivity::class.java)

            val contentPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    contentIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(
                    context,
                    context.getString(R.string.fcm_channel_id)
            )
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setContentTitle(context.getString(R.string.fcm_channel_name))
                    .setContentText(messageBody)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setAutoCancel(true)

            val manager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
            manager.notify(0, builder.build())
        }
    }
}