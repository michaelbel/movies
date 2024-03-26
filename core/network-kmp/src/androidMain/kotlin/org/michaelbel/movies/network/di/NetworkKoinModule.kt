package org.michaelbel.movies.network.di

import org.koin.dsl.module
import org.michaelbel.movies.network.AccountNetworkService
import org.michaelbel.movies.network.AuthenticationNetworkService
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.network.SearchNetworkService
import org.michaelbel.movies.network.ktor.di.ktorNetworkKoinModule
import org.michaelbel.movies.network.retrofit.di.retrofitNetworkKoinModule

val networkKoinModule = module {
    includes(
        ktorNetworkKoinModule,
        retrofitNetworkKoinModule
    )
    single { AccountNetworkService(get(), get()) }
    single { AuthenticationNetworkService(get(), get()) }
    single { MovieNetworkService(get(), get()) }
    single { SearchNetworkService(get(), get()) }
}