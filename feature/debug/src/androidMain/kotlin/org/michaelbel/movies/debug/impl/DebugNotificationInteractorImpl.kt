@file:SuppressLint("MissingPermission")

package org.michaelbel.movies.debug.impl

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.michaelbel.movies.common.ktx.isPostNotificationsPermissionGranted
import org.michaelbel.movies.common.ktx.notificationManager
import org.michaelbel.movies.debug.DebugActivity
import org.michaelbel.movies.debug.DebugNotificationInteractor
import org.michaelbel.movies.debug.R
import org.michaelbel.movies.ui.icons.MoviesAndroidIcons

internal class DebugNotificationInteractorImpl(
    private val context: Context
): DebugNotificationInteractor {

    override fun showDebugNotification() {
        createChannel(
            channelId = R.string.notification_debug_channel_id,
            channelName = R.string.notification_debug_channel_name,
            channelDescription = R.string.notification_debug_channel_description
        )

        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.notification_debug_channel_id)
        ).apply {
            setContentTitle(context.getString(R.string.notification_debug_title))
            setContentText(context.getString(R.string.notification_debug_description))
            setSmallIcon(MoviesAndroidIcons.MovieFilter24)
            setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            setGroupSummary(true)
            setGroup(GROUP_NAME)
            setContentIntent(pendingIntent())
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_LOW
            setSound(null)
        }.build()

        if (context.isPostNotificationsPermissionGranted) {
            context.notificationManager.notify(TAG, ID, notification)
        }
    }

    private fun createChannel(
        @StringRes channelId: Int,
        @StringRes channelName: Int,
        @StringRes channelDescription: Int
    ) {
        val notificationChannel = NotificationChannelCompat.Builder(
            context.getString(channelId),
            NotificationManagerCompat.IMPORTANCE_HIGH
        ).apply {
            setName(context.getString(channelName))
            setDescription(context.getString(channelDescription))
            setShowBadge(true)
        }.build()
        context.notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun pendingIntent(): PendingIntent {
        return PendingIntent.getActivity(
            context,
            ID,
            Intent(context, DebugActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private companion object {
        private const val TAG = "debug notification tag"
        private const val ID = 420
        private const val GROUP_NAME = "Debug"
    }
}