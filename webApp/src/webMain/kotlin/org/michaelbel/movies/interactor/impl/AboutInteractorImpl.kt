package org.michaelbel.movies.interactor.impl

import org.michaelbel.movies.WebAppInfo
import org.michaelbel.movies.interactor.AboutInteractor

class AboutInteractorImpl: AboutInteractor {

    override val versionName: String = WebAppInfo.VERSION_NAME

    override val versionCode: Long = WebAppInfo.VERSION_CODE
}
