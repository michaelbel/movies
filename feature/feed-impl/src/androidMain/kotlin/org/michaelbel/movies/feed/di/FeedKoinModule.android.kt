package org.michaelbel.movies.feed.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.networkManagerKoinModule
import org.michaelbel.movies.notifications.di.notificationClientKoinModule

actual val feedKoinModule = module {
    includes(
        interactorKoinModule,
        notificationClientKoinModule,
        networkManagerKoinModule
    )
    viewModel { FeedViewModel(get(), get(), get()) }
}