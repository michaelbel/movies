package org.michaelbel.movies.common.ktx

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat

val Context.notificationManager: NotificationManager
    get() = ContextCompat.getSystemService(this, NotificationManager::class.java) as NotificationManager