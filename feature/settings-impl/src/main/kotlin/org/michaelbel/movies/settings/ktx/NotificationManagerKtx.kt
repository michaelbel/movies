package org.michaelbel.movies.settings.ktx

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat

internal val Context.notificationManager: NotificationManager
    get() = ContextCompat.getSystemService(this, NotificationManager::class.java) as NotificationManager