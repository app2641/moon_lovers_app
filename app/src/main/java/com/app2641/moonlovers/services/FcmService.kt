package com.app2641.moonlovers.services

import android.util.Log
import com.app2641.moonlovers.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            Log.d("ML dev", "Message Notification Body: ${it.body}")
            NotificationUtils.sendNotification(it.body as String, applicationContext)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("ML dev", "Refreshed token: $token")
    }
}