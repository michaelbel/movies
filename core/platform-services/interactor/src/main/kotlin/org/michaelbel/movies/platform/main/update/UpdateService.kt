package org.michaelbel.movies.platform.main.update

import android.app.Activity

interface UpdateService {

    fun setUpdateAvailableListener(listener: UpdateListener)

    fun startUpdate(activity: Activity)
}