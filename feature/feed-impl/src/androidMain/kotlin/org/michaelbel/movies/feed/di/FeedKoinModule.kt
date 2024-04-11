package org.michaelbel.movies.feed.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.networkManagerKoinModule
import org.michaelbel.movies.notifications.di.notificationClientKoinModule

val feedKoinModule = module {
    includes(
        interactorKoinModule,
        notificationClientKoinModule,
        networkManagerKoinModule
    )
    viewModel { FeedViewModel(get(), get(), get(), get()) }
}