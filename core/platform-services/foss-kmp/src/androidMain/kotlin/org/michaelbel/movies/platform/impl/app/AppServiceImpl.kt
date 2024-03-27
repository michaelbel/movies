package org.michaelbel.movies.platform.impl.app

import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService

class AppServiceImpl: AppService {

    override val flavor: Flavor = Flavor.Foss

    override val isPlayServicesAvailable: Boolean = false

    override fun installApp() {}
}