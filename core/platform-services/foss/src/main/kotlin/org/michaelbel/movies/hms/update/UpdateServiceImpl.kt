package org.michaelbel.movies.hms.update

import android.app.Activity
import javax.inject.Inject
import org.michaelbel.movies.platform.main.update.UpdateListener
import org.michaelbel.movies.platform.main.update.UpdateService

internal class UpdateServiceImpl @Inject constructor(): UpdateService {

    override fun setUpdateAvailableListener(listener: UpdateListener) {}

    override fun startUpdate(activity: Activity) {}
}