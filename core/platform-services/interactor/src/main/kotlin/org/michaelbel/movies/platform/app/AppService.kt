package org.michaelbel.movies.platform.app

import org.michaelbel.movies.platform.Flavor

interface AppService {

    val flavor: Flavor

    val isPlayServicesAvailable: Boolean

    fun installApp()
}