package org.michaelbel.movies.notifications

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.annotation.StringRes
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.delay
import org.michaelbel.movies.common.ktx.isTimePasses
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.notifications.ktx.isPostNotificationsPermissionGranted
import org.michaelbel.movies.notifications.model.MoviesPush
import org.michaelbel.movies.ui.icons.MoviesIcons

@SuppressLint("MissingPermission")
class NotificationClient @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManagerCompat,
    private val interactor: Interactor
) {

    suspend fun notificationsPermissionRequired(time: Long): Boolean {
        val expireTime: Long = interactor.notificationExpireTime()
        val currentTime: Long = System.currentTimeMillis()
        val isTimePasses: Boolean = isTimePasses(ONE_DAY_MILLS, expireTime, currentTime)
        delay(time)
        return !context.isPostNotificationsPermissionGranted && isTimePasses
    }

    suspend fun updateNotificationExpireTime() {
        interactor.updateNotificationExpireTime()
    }

    fun send(push: MoviesPush) {
        createChannel(
            channelId = R.string.notification_new_movies_channel_id,
            channelName = R.string.notification_channel_name,
            channelDescription = R.string.notification_channel_description
        )

        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.notification_new_movies_channel_id)
        ).apply {
            setContentTitle(push.notificationTitle)
            setContentText(push.notificationDescription)
            setSmallIcon(MoviesIcons.MovieFilter24)
            setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            color = ContextCompat.getColor(context, R.color.primary)
            setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            setGroupSummary(true)
            setGroup(GROUP_NAME)
            setContentIntent(push.pendingIntent())
            setVibrate(VIBRATE_PATTERN)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_MAX
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        }.build()

        if (context.isPostNotificationsPermissionGranted) {
            notificationManager.notify(TAG, push.notificationId, notification)
        }
    }

    fun sendDownloadImageNotification(
        notificationId: Int,
        @StringRes contentTitleRes: Int,
        @StringRes contentTextRes: Int
    ) {
        createChannel(
            channelId = R.string.notification_gallery_download_channel_id,
            channelName = R.string.notification_gallery_channel_name,
            channelDescription = R.string.notification_gallery_channel_description
        )

        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.notification_gallery_download_channel_id)
        ).apply {
            setContentTitle(context.getString(contentTitleRes))
            setContentText(context.getString(contentTextRes))
            setSmallIcon(MoviesIcons.FileDownload24)
            setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            color = ContextCompat.getColor(context, R.color.primary)
            setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            setVibrate(VIBRATE_PATTERN)
            priority = NotificationCompat.PRIORITY_HIGH
            setSound(null)
            setOngoing(true)
            setProgress(0, 0, true)
        }.build()

        if (context.isPostNotificationsPermissionGranted) {
            notificationManager.notify(DOWNLOAD_IMAGE_NOTIFICATION_TAG, notificationId, notification)
        }
    }

    fun cancelDownloadImageNotification(notificationId: Int) {
        notificationManager.cancel(DOWNLOAD_IMAGE_NOTIFICATION_TAG, notificationId)
    }

    private fun createChannel(
        @StringRes channelId: Int,
        @StringRes channelName: Int,
        @StringRes channelDescription: Int
    ) {
        val notificationChannel: NotificationChannelCompat = NotificationChannelCompat.Builder(
            context.getString(channelId),
            NotificationManagerCompat.IMPORTANCE_HIGH
        ).apply {
            setName(context.getString(channelName))
            setDescription(context.getString(channelDescription))
            setShowBadge(true)
        }.build()
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun MoviesPush.pendingIntent(): PendingIntent {
        return PendingIntent.getActivity(
            context,
            notificationId,
            Intent(Intent.ACTION_VIEW, "movies://details/$movieId".toUri()),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private companion object {
        private const val TAG = "PUSH"
        private const val DOWNLOAD_IMAGE_NOTIFICATION_TAG = "DOWNLOAD_IMAGE"
        private const val GROUP_NAME = "App"
        private val VIBRATE_PATTERN: LongArray = longArrayOf(1000)
        private val ONE_DAY_MILLS: Long = TimeUnit.DAYS.toMillis(1)
    }
}