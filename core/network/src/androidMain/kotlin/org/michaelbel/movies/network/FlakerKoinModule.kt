package org.michaelbel.movies.network

import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import org.koin.dsl.module

internal val flakerKoinModule = module {
    single<FlakerInterceptor> { FlakerInterceptor.Builder().build() }
}