package org.michaelbel.movies.persistence.database.di

import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.AccountPersistence
import org.michaelbel.movies.persistence.database.ImagePersistence
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.PagingKeyPersistence
import org.michaelbel.movies.persistence.database.SuggestionPersistence
import org.michaelbel.movies.persistence.database.TraktDatabase
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.TraktPagingKeyPersistence

val persistenceKoinModule = module {
    includes(
        moviesDatabaseKoinModule,
        traktDatabaseKoinModule
    )
    single { AccountPersistence(get<MoviesDatabase>()) }
    single { ImagePersistence(get<MoviesDatabase>()) }
    single { MoviePersistence(get<MoviesDatabase>()) }
    single { PagingKeyPersistence(get<MoviesDatabase>()) }
    single { SuggestionPersistence(get<MoviesDatabase>()) }
    single { TraktMoviePersistence(get<TraktDatabase>()) }
    single { TraktPagingKeyPersistence(get<TraktDatabase>()) }
}
