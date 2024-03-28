package org.michaelbel.movies.common.dispatchers.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.dispatchers.impl.MoviesDispatchersImpl

val dispatchersKoinModule = module {
    singleOf(::MoviesDispatchersImpl) { bind<MoviesDispatchers>() }
}