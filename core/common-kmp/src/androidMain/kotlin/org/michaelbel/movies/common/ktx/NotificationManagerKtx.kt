package org.michaelbel.movies.common.ktx

import android.content.Context
import androidx.core.app.NotificationManagerCompat

actual val Context.notificationManager: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)