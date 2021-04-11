package com.roblox.ipo.notifications

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.roblox.ipo.data.remote.IpoApiService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var apiService: IpoApiService

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.d("FCM TOKEN: ${p0}")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val notificationBuilder =
            NotificationCompat.Builder(this, p0.notification?.channelId ?: "")
                .setContentTitle(p0.notification?.title)
                .setContentText(p0.notification?.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_EVENT)

        with(NotificationManagerCompat.from(this)) {
            notify(0, notificationBuilder.build())
        }
    }

}