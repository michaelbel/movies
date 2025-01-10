package org.michaelbel.movies.interactor.impl

import android.content.Context
import org.michaelbel.movies.common.ktx.versionCode
import org.michaelbel.movies.common.ktx.versionName
import org.michaelbel.movies.interactor.AboutInteractor

class AboutInteractorImpl(
    private val context: Context
): AboutInteractor {

    override val versionName: String
        get() = context.versionName

    override val versionCode: Long
        get() = context.versionCode
}