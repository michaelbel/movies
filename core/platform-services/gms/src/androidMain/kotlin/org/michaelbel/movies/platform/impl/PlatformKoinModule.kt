package org.michaelbel.movies.platform.impl

import org.koin.dsl.module

val platformKoinModule = module {
    includes(
        firebaseKoinModule,
        googleApiKoinModule,
        playKoinModule
    )
}