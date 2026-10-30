package org.michaelbel.movies.common.trakt.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.common.trakt.TraktAuthInteractor
import org.michaelbel.movies.common.trakt.impl.TraktAuthInteractorImpl

actual val traktAuthKoinModule = module {
    singleOf(::TraktAuthInteractorImpl) { bind<TraktAuthInteractor>() }
}
