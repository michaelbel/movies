package org.michaelbel.movies.settings.about.impl

import android.content.Context
import org.michaelbel.movies.settings.about.AboutManager
import org.michaelbel.movies.settings.ktx.versionCode
import org.michaelbel.movies.settings.ktx.versionName

class AboutManagerImpl(
    private val context: Context
): AboutManager {

    override val versionName: String
        get() = context.versionName

    override val versionCode: Long
        get() = context.versionCode
}