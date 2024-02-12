package org.michaelbel.movies.notifications.ktx

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import org.michaelbel.movies.common.ktx.notificationManager

internal val Context.isPostNotificationsPermissionGranted: Boolean
    get() = if (Build.VERSION.SDK_INT >= 33) {
        ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        notificationManager.areNotificationsEnabled()
    }