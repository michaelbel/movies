@file:OptIn(ExperimentalWasmJsInterop::class)

package org.michaelbel.movies.persistence.database

import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.w3c.dom.Worker
import kotlin.js.ExperimentalWasmJsInterop

actual fun createWebWorkerSQLiteDriver(): WebWorkerSQLiteDriver {
    return WebWorkerSQLiteDriver(sqliteWebWorker())
}

// Kotlin/Wasm requires js(code) to be the single expression of a top-level function body.
private fun sqliteWebWorker(): Worker =
    js("""new Worker(new URL("@michaelbel/movies-sqlite-web-worker/worker.js", import.meta.url))""")
