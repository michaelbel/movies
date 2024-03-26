package org.michaelbel.movies.platform.impl.app

import javax.inject.Inject
import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService

class AppServiceImpl @Inject constructor(): AppService {

    override val flavor: Flavor = Flavor.Hms

    override val isPlayServicesAvailable: Boolean
        get() = false

    override fun installApp() {}
}