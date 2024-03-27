package org.michaelbel.movies.network.ktor

import org.koin.dsl.module

val ktorNetworkKoinModule = module {
    includes(
        ktorKoinModule
    )
    single { KtorAccountService(get()) }
    single { KtorAuthenticationService(get()) }
    single { KtorMovieService(get()) }
    single { KtorSearchService(get()) }
}