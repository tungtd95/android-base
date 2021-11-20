package com.sekiro.utils

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        NotificationUtils.showGeneralNotification(this, p0)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}