package org.michaelbel.movies.feed.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.connectivityKoinModule
import org.michaelbel.movies.notifications.di.notificationClientKoinModule

val feedKoinModule = module {
    includes(
        interactorKoinModule,
        notificationClientKoinModule,
        connectivityKoinModule
    )
    viewModelOf(::FeedViewModel)
}