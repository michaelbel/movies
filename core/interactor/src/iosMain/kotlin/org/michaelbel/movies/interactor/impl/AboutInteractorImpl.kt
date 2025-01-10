package org.michaelbel.movies.interactor.impl

import org.michaelbel.movies.interactor.AboutInteractor

class AboutInteractorImpl: AboutInteractor {

    override val versionName: String
        get() = "1.0.0"

    override val versionCode: Long
        get() = 1L
}