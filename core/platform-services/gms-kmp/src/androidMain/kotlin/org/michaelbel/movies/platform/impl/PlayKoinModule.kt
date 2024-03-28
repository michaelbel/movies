package org.michaelbel.movies.platform.impl

import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.review.ReviewManagerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.platform.impl.update.InAppUpdate

val playKoinModule = module {
    single { ReviewManagerFactory.create(androidContext()) }
    single { AppUpdateManagerFactory.create(androidContext()) }
    single { InAppUpdate(get(), get()) }
}