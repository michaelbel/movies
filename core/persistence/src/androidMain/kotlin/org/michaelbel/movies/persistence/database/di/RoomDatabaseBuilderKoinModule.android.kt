package org.michaelbel.movies.persistence.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual val roomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<AppDatabase>> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        )
    }
}