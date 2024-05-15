package org.michaelbel.movies.network

import org.koin.dsl.module
import org.michaelbel.movies.network.okhttp.ApikeyInterceptor

internal val apiKeyInterceptorKoinModule = module {
    single<ApikeyInterceptor> { ApikeyInterceptor(BuildConfig.TMDB_API_KEY) }
}