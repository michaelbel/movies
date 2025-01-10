package org.michaelbel.movies.common.notify.impl

import android.content.Context
import org.michaelbel.movies.common.ktx.notificationManager
import org.michaelbel.movies.common.notify.NotifyManager

internal class NotifyManagerImpl(
    private val context: Context
): NotifyManager {

    override val areNotificationsEnabled: Boolean
        get() = context.notificationManager.areNotificationsEnabled()
}