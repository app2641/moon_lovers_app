package com.example.moonlovers.services

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.moonlovers.utils.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data?.let {
            Log.e(TAG, "Message data payload" + remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.e(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Log.e(TAG, "Refreshed token: $token")

        sendRegistrationServer(token)
    }

    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }

    private fun sendRegistrationServer(token: String) {

    }

    companion object {
        private const val TAG = "FcmService"
    }
}