package org.michaelbel.movies.common.coroutines

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(
    val dispatcher: MoviesDispatchers
)

enum class MoviesDispatchers {
    Default,
    IO,
    Main,
    MainImmediate
}