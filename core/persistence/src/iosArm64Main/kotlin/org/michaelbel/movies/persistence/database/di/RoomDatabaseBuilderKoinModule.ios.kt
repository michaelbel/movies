package org.michaelbel.movies.persistence.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.AppDatabase
import org.michaelbel.movies.persistence.database.db.AppDatabase_Impl
import platform.Foundation.NSHomeDirectory

actual val roomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<AppDatabase>> {
        Room.databaseBuilder<AppDatabase>(
            name = "${NSHomeDirectory()}/${AppDatabase.DATABASE_NAME}",
            factory =  { instantiateAppDatabase() }
        ).setDriver(BundledSQLiteDriver())
    }
}

/**
 * Note that the instantiateImpl is generated in the same package as the @Database annotated class,
 * so you might need an import if used from a different package: import <database-package>.instantiateImpl.
 *
 * Don't create Koin single's for AppDatabase and all Dao's!
 *
 * Generate instantiateImpl:
 * ./gradlew :core:persistence:compileCommonMainKotlinMetadata
 */
private fun instantiateAppDatabase(): AppDatabase {
    return AppDatabase_Impl()
}