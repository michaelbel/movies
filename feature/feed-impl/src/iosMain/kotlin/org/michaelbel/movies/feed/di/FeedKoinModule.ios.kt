package org.michaelbel.movies.feed.di

import org.koin.dsl.module
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule

actual val feedKoinModule = module {
    includes(
        interactorKoinModule
    )
    single { FeedViewModel(get()) }
}