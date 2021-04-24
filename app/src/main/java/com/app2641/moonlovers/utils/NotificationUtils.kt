package com.app2641.moonlovers.utils

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.app2641.moonlovers.MainActivity
import com.app2641.moonlovers.R

@SuppressLint("ResourceAsColor")
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.fcm_channel_id)
    )
            .setSmallIcon(R.drawable.ic_moon_lovers_notification)
            .setColor(R.color.grey_500)
            .setContentTitle(applicationContext.getString(R.string.fcm_channel_name))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setLargeIcon(ResourceUtils.getBitmap(applicationContext, R.drawable.img_moon15))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)

    notify(0, builder.build())
}