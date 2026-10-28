package org.michaelbel.movies.persistence.database

import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.w3c.dom.Worker

actual fun createWebWorkerSQLiteDriver(): WebWorkerSQLiteDriver {
    return WebWorkerSQLiteDriver(
        Worker(js("""new URL("@michaelbel/movies-sqlite-web-worker/worker.js", import.meta.url)"""))
    )
}
