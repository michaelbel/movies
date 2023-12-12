package org.michaelbel.movies.hms.app

import javax.inject.Inject
import org.michaelbel.movies.platform.main.app.AppService

internal class AppServiceImpl @Inject constructor(): AppService {

    override val isPlayServicesAvailable: Boolean
        get() = false

    override fun installApp() {}
}