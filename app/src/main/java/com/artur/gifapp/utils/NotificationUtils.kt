package com.artur.gifapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.artur.gifapp.R
import java.io.File

// Notification ID.
private val NOTIFICATION_ID = 0
private val CHANNEL_ID = "channelId"
private val CHANNEL_NAME = "Gif download"
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(
    context: Context,
    notificationManager: NotificationManager,
    file: File
) {

    val intent = Intent(Intent.ACTION_VIEW)
    val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
    intent.setDataAndType(uri, "image/gif")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    val desc = context.getString(R.string.notification_description)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel: NotificationChannel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = desc
            }

        // Register the channel with the system
        notificationManager.createNotificationChannel(channel)
    }

    // Build the notification
    val builder = NotificationCompat.Builder(
        context,
        CHANNEL_ID
    ).setSmallIcon(R.drawable.ic_download)
        .setContentTitle(
            context.getString(R.string.app_name)
        )
        .setContentText(desc)
        .setAutoCancel(true)
        .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)


    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}