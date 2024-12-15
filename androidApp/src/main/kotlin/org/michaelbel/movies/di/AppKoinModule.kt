package org.michaelbel.movies.di

import org.koin.dsl.module
import org.michaelbel.movies.account.di.accountKoinModule
import org.michaelbel.movies.auth.di.authKoinModule
import org.michaelbel.movies.debug.di.debugKoinModule
import org.michaelbel.movies.details.di.detailsKoinModule
import org.michaelbel.movies.feed.di.feedKoinModule
import org.michaelbel.movies.gallery.di.galleryKoinModule
import org.michaelbel.movies.main.di.mainKoinModule
import org.michaelbel.movies.main.di.mainNavKoinModule
import org.michaelbel.movies.platform.inject.flavorServiceKtorModule
import org.michaelbel.movies.search.di.searchKoinModule
import org.michaelbel.movies.settings.di.settingsKoinModule
import org.michaelbel.movies.widget.di.glanceKoinModule

internal val appKoinModule = module {
    includes(
        flavorServiceKtorModule,
        mainKoinModule,
        mainNavKoinModule,
        accountKoinModule,
        authKoinModule,
        detailsKoinModule,
        feedKoinModule,
        galleryKoinModule,
        searchKoinModule,
        settingsKoinModule,
        debugKoinModule,
        glanceKoinModule
    )
}