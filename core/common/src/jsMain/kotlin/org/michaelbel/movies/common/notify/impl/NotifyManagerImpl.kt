package org.michaelbel.movies.common.notify.impl

import org.michaelbel.movies.common.notify.NotifyManager

internal class NotifyManagerImpl: NotifyManager {

    override val areNotificationsEnabled: Boolean
        get() = false
}