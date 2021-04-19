package com.app2641.moonlovers.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.app2641.moonlovers.MainActivity
import com.app2641.moonlovers.R

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val icon = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.img_moon15
    )

    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.fcm_channel_id)
    )
            .setSmallIcon(R.drawable.img_moon15)
            .setContentTitle(applicationContext.getString(R.string.fcm_channel_name))
            .setContentIntent(contentPendingIntent)
            .setLargeIcon(icon)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)

    notify(0, builder.build())
}