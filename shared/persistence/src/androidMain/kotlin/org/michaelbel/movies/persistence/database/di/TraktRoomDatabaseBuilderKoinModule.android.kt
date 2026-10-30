package org.michaelbel.movies.persistence.database.di

import androidx.room3.Room
import androidx.room3.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase

actual val traktRoomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<TraktAppDatabase>>(named("trakt")) {
        Room.databaseBuilder(
            context = androidContext(),
            klass = TraktAppDatabase::class.java,
            name = TraktAppDatabase.DATABASE_NAME
        )
    }
}
