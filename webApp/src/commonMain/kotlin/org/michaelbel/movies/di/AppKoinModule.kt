package org.michaelbel.movies.di

import org.koin.dsl.module
import org.michaelbel.movies.main.di.mainKoinModule
import org.michaelbel.movies.platform.inject.flavorServiceKtorModule

val appKoinModule = module {
    includes(
        flavorServiceKtorModule,
        mainKoinModule
    )
}