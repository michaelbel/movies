package org.michaelbel.movies.network.di

import org.koin.dsl.module
import org.michaelbel.movies.network.AccountNetworkService
import org.michaelbel.movies.network.AuthenticationNetworkService
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.network.SearchNetworkService
import org.michaelbel.movies.network.ktor.ktorNetworkKoinModule

val networkKoinModule = module {
    includes(
        ktorNetworkKoinModule
    )
    single { AccountNetworkService(get()) }
    single { AuthenticationNetworkService(get()) }
    single { MovieNetworkService(get()) }
    single { SearchNetworkService(get()) }
}