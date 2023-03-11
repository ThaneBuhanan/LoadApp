package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity.Companion.FILENAME
import com.udacity.DetailActivity.Companion.STATUS

const val NOTIFICATION_ID = 69420

fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context,
    fileName: String,
    status: String,
) {

    val detailIntent = Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra(FILENAME, fileName)
        putExtra(STATUS, status)
    }

    val detailPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        detailIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.load_app_channel_id)
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setAutoCancel(true)
        .addAction(
            R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.go_to_details),
            detailPendingIntent,
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    notify(NOTIFICATION_ID, builder.build())

}