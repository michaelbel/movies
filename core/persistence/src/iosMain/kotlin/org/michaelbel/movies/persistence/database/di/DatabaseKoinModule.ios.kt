package org.michaelbel.movies.persistence.database.di

import org.koin.dsl.module

actual val databaseKoinModule = module {
    //single<AppDatabase> { createRoomDatabase().build() }
}

/*
private fun createRoomDatabase(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = NSHomeDirectory() + "/${AppDatabase.DATABASE_NAME}"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() } // IDE may show error but there is none.
    )
}*/
