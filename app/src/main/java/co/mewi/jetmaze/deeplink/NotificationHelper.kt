package co.mewi.jetmaze.deeplink

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import co.mewi.jetmaze.R
import co.mewi.jetmaze.fragments.ShareFragment

object NotificationHelper {

    fun imitatePushNotification(context: Context) {
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_nav_graph)
            .setDestination(R.id.shareFragment)
            .setArguments(Bundle().apply { putBoolean(ShareFragment.ARG_IS_DEEPLINK, true) })
            .createPendingIntent()

        //   pendingIntent.send()
        sendPush(context, pendingIntent)
    }


    private fun sendPush(context: Context, intent: PendingIntent) {
        val CHANNEL_ID = "1A"
        val NOTIFICATION_ID = 1234

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Free Pass")
            .setContentText("You win a free pass to the end. Share now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(intent)
            .build()

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "Shortcuts",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}