package org.michaelbel.movies.network.flaker.di

import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import org.koin.dsl.module

val flakerKoinModule = module {
    single { FlakerInterceptor.Builder().build() }
}