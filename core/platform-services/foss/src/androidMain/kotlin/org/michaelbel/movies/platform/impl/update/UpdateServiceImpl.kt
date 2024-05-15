@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.platform.impl.update

import android.app.Activity
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService

actual class UpdateServiceImpl: UpdateService {

    override fun setUpdateAvailableListener(listener: UpdateListener) {}

    override fun startUpdate(activity: Activity) {}
}