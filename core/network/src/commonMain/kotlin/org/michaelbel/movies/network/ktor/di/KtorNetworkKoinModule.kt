package org.michaelbel.movies.network.ktor.di

import org.koin.dsl.module
import org.michaelbel.movies.network.ktor.KtorAccountService
import org.michaelbel.movies.network.ktor.KtorAuthenticationService
import org.michaelbel.movies.network.ktor.KtorMovieService
import org.michaelbel.movies.network.ktor.KtorSearchService

val ktorNetworkKoinModule = module {
    includes(
        ktorKoinModule
    )
    single { KtorAccountService(get()) }
    single { KtorAuthenticationService(get()) }
    single { KtorMovieService(get()) }
    single { KtorSearchService(get()) }
}