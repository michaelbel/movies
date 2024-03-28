package org.michaelbel.movies.platform.impl.update

import android.app.Activity
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService

class UpdateServiceImpl: UpdateService {

    override fun setUpdateAvailableListener(listener: UpdateListener) {}

    override fun startUpdate(activity: Activity) {}
}