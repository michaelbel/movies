package org.michaelbel.movies.persistence.database.di

import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.AccountPersistence
import org.michaelbel.movies.persistence.database.ImagePersistence
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.PagingKeyPersistence
import org.michaelbel.movies.persistence.database.SuggestionPersistence

val persistenceKoinModule = module {
    includes(
        daoKoinModule
    )
    single { AccountPersistence(get()) }
    single { ImagePersistence(get()) }
    single { MoviePersistence(get()) }
    single { PagingKeyPersistence(get()) }
    single { SuggestionPersistence(get()) }
}