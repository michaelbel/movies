package org.michaelbel.movies.platform.update

import android.app.Activity

interface UpdateService {

    fun setUpdateAvailableListener(listener: UpdateListener)

    fun startUpdate(activity: Activity)
}