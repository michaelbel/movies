package org.michaelbel.movies.settings.about.impl

import org.michaelbel.movies.settings.about.AboutManager

class AboutManagerImpl: AboutManager {

    override val versionName: String
        get() = "1.0.0"

    override val versionCode: Long
        get() = 1L
}