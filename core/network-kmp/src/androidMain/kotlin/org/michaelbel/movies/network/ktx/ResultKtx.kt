package org.michaelbel.movies.network.ktx

import org.michaelbel.movies.network.model.Result

actual val <T> Result<T>.nextPage: Int
    get() = page.plus(1)

actual val <T> Result<T>.isPaginationReached: Boolean
    get() = page == totalPages

actual val <T> Result<T>.isEmpty: Boolean
    get() = page == 1 && results.isEmpty()