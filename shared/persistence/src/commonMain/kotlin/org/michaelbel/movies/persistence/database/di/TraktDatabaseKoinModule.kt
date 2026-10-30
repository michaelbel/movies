package org.michaelbel.movies.persistence.database.di

import androidx.room3.RoomDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.TraktDatabase
import org.michaelbel.movies.persistence.database.createTraktDatabase
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase

val traktDatabaseKoinModule = module {
    includes(
        traktRoomDatabaseBuilderModule
    )
    single<TraktDatabase> { createTraktDatabase(get<RoomDatabase.Builder<TraktAppDatabase>>(named("trakt"))) }
}
