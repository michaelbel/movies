package org.michaelbel.movies.persistence.database

import androidx.sqlite.driver.web.WebWorkerSQLiteDriver

/**
 * Creates a [WebWorkerSQLiteDriver] backed by the `@michaelbel/movies-sqlite-web-worker` npm package
 * located in `shared/persistence/web-worker`.
 */
expect fun createWebWorkerSQLiteDriver(): WebWorkerSQLiteDriver
