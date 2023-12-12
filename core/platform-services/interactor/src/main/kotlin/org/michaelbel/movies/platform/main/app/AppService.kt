package org.michaelbel.movies.platform.main.app

interface AppService {

    val isPlayServicesAvailable: Boolean

    fun installApp()
}