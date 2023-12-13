package org.michaelbel.movies.platform.main.app

import org.michaelbel.movies.platform.main.Flavor

interface AppService {

    val flavor: Flavor

    val isPlayServicesAvailable: Boolean

    fun installApp()
}