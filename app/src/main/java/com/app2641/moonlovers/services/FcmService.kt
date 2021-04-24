package com.app2641.moonlovers.services

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.app2641.moonlovers.utils.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data?.let {
            Log.d("ML dev", "Message data payload" + remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.d("ML dev", "Message Notification Body: ${it.body}")
            sendNotification(it.body as String)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("ML dev", "Refreshed token: $token")

        sendRegistrationServer(token)
    }

    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }

    private fun sendRegistrationServer(token: String) {

    }
}