package org.michaelbel.movies.platform.impl.update

import android.app.Activity
import javax.inject.Inject
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService

class UpdateServiceImpl @Inject constructor(): UpdateService {

    override fun setUpdateAvailableListener(listener: UpdateListener) {}

    override fun startUpdate(activity: Activity) {}
}