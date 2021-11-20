package com.sekiro.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.sekiro.R

object NotificationUtils {

    private const val CHANNEL_ORDER = "CHANNEL_ORDER"
    private const val NOTIFICATION_ORDER = 1

    fun subscribeToPostNotification(postId: Long) {
        FirebaseMessaging.getInstance().subscribeToTopic("posts_$postId")
            .addOnSuccessListener {
                Log.i("NotificationUtils", "subscribed to post $postId's notification")
            }
            .addOnFailureListener {
                Log.i("NotificationUtils", "subscribe error: ${it.message}")
            }
            .addOnCompleteListener {
                Log.i("NotificationUtils", "complete subscribe to post $postId's notification")
            }
    }

    fun removeSubscribeToPostNotification(postId: Long) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("posts_$postId")
            .addOnSuccessListener {
                Log.i("NotificationUtils", "unsubscribe from post $postId's notification")
            }
            .addOnFailureListener {
                Log.i("NotificationUtils", "unsubscribe error: ${it.message}")
            }
            .addOnCompleteListener {
                Log.i("NotificationUtils", "complete unsubscribe to post $postId's notification")
            }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showGeneralNotification(context: Context, remoteMessage: RemoteMessage) {
        var title = remoteMessage.data["title"].orEmpty()
        if (title.isBlank()) title = remoteMessage.notification?.title.orEmpty()
        var body = remoteMessage.data["body"].orEmpty()
        if (body.isBlank()) body = remoteMessage.notification?.body.orEmpty()
        val deepLink = remoteMessage.data["deeplink"].orEmpty()

        val notificationIntent = Intent(Intent.ACTION_VIEW)
        notificationIntent.data = Uri.parse(deepLink)
        val contentIntent =
            PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

        val builder = NotificationCompat.Builder(context, CHANNEL_ORDER)
            .setSmallIcon(R.drawable.ic_baseline_adb_24)
            .setContentTitle(title)
            .setContentIntent(contentIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel order"
            val descriptionText = "channel for display order info"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ORDER, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ORDER, builder.build())
        }
    }
}