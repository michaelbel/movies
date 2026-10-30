package org.michaelbel.movies.persistence.database.di

import androidx.room3.Room
import androidx.room3.RoomDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase_Impl

actual val traktRoomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<TraktAppDatabase>>(named("trakt")) {
        Room.databaseBuilder<TraktAppDatabase>(
            name = traktDocumentDatabasePath(),
            factory = { TraktAppDatabase_Impl() }
        ).setDriver(createDocumentsDriver())
    }
}
