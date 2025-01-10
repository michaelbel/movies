package org.michaelbel.movies.feed.di

import org.koin.dsl.module
import org.michaelbel.movies.feed.FeedViewModel

actual val feedKoinModule = module {
    single { FeedViewModel() }
}