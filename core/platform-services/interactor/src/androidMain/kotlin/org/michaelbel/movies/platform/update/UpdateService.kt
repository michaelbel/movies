@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.platform.update

import android.app.Activity

actual interface UpdateService {

    fun setUpdateAvailableListener(listener: UpdateListener)

    fun startUpdate(activity: Activity)
}